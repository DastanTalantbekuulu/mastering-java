package com.github.spranshu1.common.util.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generic Serializer/Deserializer class.
 *
 * @param <T> the type of the concerned object which is to be serialized or to be de-serialized to. Use builder to create instance.
 */
public class JsonParser<T> {
    private static final Logger log = LoggerFactory.getLogger(JsonParser.class);

    private final ObjectMapper mapper = new ObjectMapper();

    private ObjectReader reader;

    private ObjectWriter writer;

    /**
     * The type Json parser builder.
     *
     * @param <T> the type parameter
     */
    public static class JsonParserBuilder<T> {
        private SimpleModule module;
        private JavaType type;
        private List<Feature> features = new ArrayList<>();

        /**
         * Constructs an {@code JsonObjectParser} object with {@code module}
         *
         * @param module the module
         */
        public JsonParserBuilder(SimpleModule module) {
            this.module = module;
        }


        /**
         * Constructs an {@code JsonObjectParser} object adding the {@code type}
         * to the reader
         *
         * @param type the type
         */
        public JsonParserBuilder(JavaType type) {
            this.type = type;
            this.module = new SimpleModule();
        }

        /**
         * Constructs an {@code JsonObjectParser} object adding the
         * {@code readingClass} to the reader
         *
         * @param readingClass Concerned object of the json structure
         */
        public JsonParserBuilder(Class<T> readingClass) {
            type = new ObjectMapper().getTypeFactory().constructType(readingClass);
            module = new SimpleModule();
        }


        /**
         * Adds a {@link JsonSerializer} of {@code S} type to the
         * {@code JsonObjectParser} object
         * <p>
         * Serializer need not be added when the object is configured with json
         * annotations
         *
         * @param <S>              the type parameter
         * @param serializerEntity Class to be considered while serializing the {@code Type}            object
         * @param serializer       Prefered serializer for serializing the object
         * @return the json parser builder
         */
        public <S> JsonParserBuilder<T> withSerializer(Class<S> serializerEntity, JsonSerializer<S> serializer) {
            module.addSerializer(serializerEntity, serializer);
            return this;
        }

        /**
         * Adds a {@link JsonDeserializer} of {@code S} type to the
         * {@code JsonObjectParser} object
         * <p>
         * Deserializer need not be added when the object is configured with
         * json annotations
         *
         * @param <S>                the type parameter
         * @param deserializerEntity Class to be considered while deserializing the string to            the {@code Type} object
         * @param deserializer       Prefered deserializer to deserialize the string
         * @return the json parser builder
         */
        public <S> JsonParserBuilder<T> withDeSerializer(Class<S> deserializerEntity, JsonDeserializer<S> deserializer) {
            module.addDeserializer(deserializerEntity, deserializer);
            return this;
        }

        /**
         * Constructs an {@code JsonObjectParser} object adding the
         * {@code readingClass} to the reader
         *
         * @param featureVarArray the feature var array
         * @return the json parser builder
         */
        public JsonParserBuilder<T> withFeatures(Feature... featureVarArray) {
            Collections.addAll(features, featureVarArray);
            return this;
        }

        /**
         * Build json parser.
         *
         * @return the json parser
         */
        public JsonParser<T> build() {
            this.features.add(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER);
            final JsonParser<T> parser = new JsonParser<>();
            for (Feature feature : features) {
                parser.mapper.configure(feature, true);
            }
            parser.mapper.registerModule(module);
            parser.reader = parser.mapper.readerFor(type);
            parser.writer = parser.mapper.writer();
            return parser;
        }
    }

    /**
     * Intentionally kept private use {@link JsonParserBuilder}
     */
    private JsonParser() {
    }

    /**
     * Deserialize the {@code jsonStr} to {@code Type} object.
     *
     * @param jsonStr Json string
     * @return Deserialized object
     * @throws IOException the io exception
     */
    public T getJavaObject(String jsonStr) throws IOException {
        try {
            return reader.readValue(jsonStr);
        } catch (JsonParseException e) {
            log.error("JsonParseException in parsing json {}, ", jsonStr, e);
            throw e;
        } catch (JsonMappingException e) {
            log.error("JsonMappingException in parsing json {}, ", jsonStr, e);
            throw e;
        } catch (IOException e) {
            log.error("IOException in parsing json {}, ", jsonStr, e);
            throw e;
        }
    }

    /**
     * Gets java objects.
     *
     * @param jsonStr the json str
     * @return the java objects
     * @throws IOException the io exception
     */
    public List<T> getJavaObjects(String jsonStr) throws IOException {
        try {
            List<T> objList = new ArrayList<>();
            try (MappingIterator<T> jsonObjIter = reader.readValues(jsonStr)) {
                while (jsonObjIter.hasNext()) {
                    objList.add(jsonObjIter.nextValue());
                }
                return objList;
            }
        } catch (JsonParseException e) {
            log.error("JsonParseException in parsing json {}, ", jsonStr, e);
            throw e;
        } catch (JsonMappingException e) {
            log.error("JsonMappingException in parsing json {}, ", jsonStr, e);
            throw e;
        } catch (IOException e) {
            log.error("IOException in parsing json {}, ", jsonStr, e);
            throw e;
        }

    }

    /**
     * Read json object node from the input stream.
     *
     * @param stream the stream
     * @return jsonNode. json node from stream
     * @throws IOException the io exception
     */
    public JsonNode getJsonNodeFromStream(InputStream stream) throws IOException {
        try {
            return reader.readValue(stream);
        } catch (JsonParseException e) {
            log.error("JsonParseException in parsing json ", e);
            throw e;
        } catch (JsonMappingException e) {
            log.error("JsonMappingException in parsing json ", e);
            throw e;
        } catch (IOException e) {
            log.error("IOException in parsing json ", e);
            throw e;
        }
    }

    /**
     * Deserialize the jsonStr to java object of the input type beanClass.
     *
     * @param jsonStr the json str
     * @return instance of the type beanClass.
     * @throws IOException the io exception
     */
    public JsonNode readJsonNodeTree(String jsonStr) throws IOException {
        try {
            return mapper.readTree(jsonStr);
        } catch (JsonParseException e) {
            log.error("JsonParseException in parsing json {}, ", jsonStr, e);
            throw e;
        } catch (JsonMappingException e) {
            log.error("JsonMappingException in parsing json {}, ", jsonStr, e);
            throw e;
        } catch (IOException e) {
            log.error("IOException in parsing json {}, ", jsonStr, e);
            throw e;
        }
    }

    /**
     * Create a object node.
     *
     * @return the object node
     */
    public ObjectNode createNode() {
        return mapper.createObjectNode();
    }



    /**
     * Create a array node.
     *
     * @return the array node
     */
    public ArrayNode createArray() {
        return mapper.createArrayNode();
    }

    /**
     * Create a object node.
     *
     * @param obj the obj
     * @return the object node
     */
    public ObjectNode readPojoToNode(Object obj) {
        return mapper.valueToTree(obj);
    }

    /**
     * Create a object node.
     *
     * @param obj the obj
     * @return the tree node
     */
    public TreeNode readPojoToTreeNode(Object obj) {
        return mapper.valueToTree(obj);
    }

    /**
     * Serialize the annotated pojo object to json string.
     *
     * @param obj the obj
     * @return instance of the annotated pojo.
     * @throws JsonProcessingException the json processing exception
     */
    public String getJsonFromAnnotatedPojo(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    /**
     * Serialize the annotated pojo object to json string.
     *
     * @param obj the obj
     * @return instance of the annotated pojo.
     * @throws JsonProcessingException the json processing exception
     */
    public String getJsonFromAnnotatedPojoWithRoot(Object obj) throws JsonProcessingException {
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String json = mapper.writeValueAsString(obj);
        mapper.disable(SerializationFeature.WRAP_ROOT_VALUE);
        return json;
    }

    /**
     * Returns the serialized object for the pojo.
     *
     * @param object Object to be serialized
     * @return Serialized version of the pojo in the form of a json string
     * @throws IOException the io exception
     */
    public String buildJSONFromJavaObject(T object) throws IOException {
        try {
            return writer.writeValueAsString(object);
        } catch (JsonParseException e) {
            log.error("JsonParseException in parsing json  ", e);
            throw e;
        } catch (JsonMappingException e) {
            log.error("JsonMappingException in parsing json  ", e);
            throw e;
        } catch (IOException e) {
            log.error("IOException in parsing json  ", e);
            throw e;
        }
    }

}