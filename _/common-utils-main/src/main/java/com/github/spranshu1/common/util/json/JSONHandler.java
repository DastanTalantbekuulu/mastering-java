/*
 * Created By: Pranshu Shrivastava

 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.spranshu1.common.util.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.spranshu1.common.util.date.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * The utility class, having functions that can convert JSON data into POJO and vice versa.
 * <p>
 * It uses Fasterxml Jackson library for JSON handling.
 */
public final class JSONHandler {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(JSONHandler.class);

    /**
     * The object mapper, used later for JSON conversion.
     */
    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();
    /**
     * The empty string constant
     */
    private static final String EMPTY_STRING = "";
    /**
     * The number sql types list
     */
    private static final List<Integer> numberSQLTypes = Arrays.asList(new Integer[]{-6, -5, 2, 4, 5});
    /**
     * The decimal sql types list
     */
    private static final List<Integer> decimalSQLTypes = Arrays.asList(new Integer[]{3, 6, 7, 8});
    /**
     * The json factory, used for getting instance of json generator
     */
    private static final JsonFactory factory = new JsonFactory();

    static {
        OBJ_MAPPER.configure(Feature.IGNORE_UNDEFINED, true);
        OBJ_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJ_MAPPER.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        //OBJ_MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        OBJ_MAPPER.setSerializationInclusion(Include.NON_NULL);
    }

    /**
     * Instantiates a new JSON handler.
     */
    private JSONHandler() {
        // constructor intentionally kept empty
    }

    /**
     * Create a json string from the input result set.
     *
     * @param rs {@link ResultSet}
     * @return json string
     * @throws SQLException the sql exception
     * @throws IOException  the IO exception
     */
    public static String createJsonFromResultSet(final ResultSet rs) throws Exception {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             JsonGenerator generator = factory.createGenerator(baos)) {
            if (rs.next()) {
                rs.previous();
            } else {
                return EMPTY_STRING;
            }
            generator.writeStartArray();
            while (rs.next()) {
                writeRow(rs, generator);
            }
            generator.writeEndArray();
            generator.close();
            return baos.toString();
        }

    }

    /**
     * Write resultset row to json without null.
     *
     * @param rs        the resultset
     * @param generator the json generator
     * @throws IOException  the IO exception
     * @throws SQLException the sql exception
     */
    private static void writeRow(final ResultSet rs, final JsonGenerator generator) throws IOException, SQLException {
        generator.writeStartObject();
        int noOfColumns = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= noOfColumns; i++) {
            generator.writeFieldName(rs.getMetaData().getColumnLabel(i));
            // 93 for timestamp type column
            if (rs.getMetaData().getColumnType(i) == 93) {
                Timestamp ts = rs.getTimestamp(i);
                if (ts != null)
                    generator.writeObject(DateTimeUtil.timestampToString(new Date(ts.getTime())));
                else
                    generator.writeString(EMPTY_STRING);
                // 91 for date type column
            } else if (rs.getMetaData().getColumnType(i) == 91) {
                Date date = rs.getDate(i);
                if (date != null)
                    // dateFormat.format(new Date(date.getTime()))
                    generator.writeObject(DateTimeUtil.dateToString(new Date(date.getTime())));
                else
                    generator.writeString(EMPTY_STRING);
            } else {
                if (rs.getObject(i) != null) {
                    if (numberSQLTypes.contains(rs.getMetaData().getColumnType(i))) {
                        generator.writeNumber(rs.getLong(i));
                    } else if (decimalSQLTypes.contains(rs.getMetaData().getColumnType(i))) {
                        generator.writeNumber(rs.getDouble(i));
                    } else {
                        generator.writeString(rs.getObject(i).toString());
                    }
                } else {
                    generator.writeString(EMPTY_STRING);
                }

            }
        }
        generator.writeEndObject();
    }

    /**
     * Merge any number of ObjectNode
     *
     * @param jsonObjects the json object to merge
     * @return merged jsonObject
     */
    @SuppressWarnings("unchecked")
    public static ObjectNode mergeJsons(final ObjectNode... jsonObjects) {
        ObjectNode result = OBJ_MAPPER.createObjectNode();
        for (ObjectNode object : jsonObjects) {
            if (object != null)
                result.setAll(object);
        }
        return result;
    }

    /**
     * Converts given POJO into JSON String.
     * <p>
     * Example,
     * <pre><code>
     * 	// Create POJO
     * 	StandardResponse resObj = new StandardResponse();
     * 	resObj.setResCode(CommonsConstants.ERR_CODE_401_UNAUTH);
     * 	resObj.setResMsg(CommonsConstants.ERR_STR_401_UNAUTH);
     *
     * 	// Convert POJO to JSON String
     * 	String jsonStr = JSONHandler.<b>toJson</b>(jsonObj);
     * </code></pre>
     *
     * @param object to be transformed to JSON string
     * @return the JSON string, or null in case of exception
     */
    public static String toJson(final Object object) {
        String payloadJson = null;
        try {
            payloadJson = OBJ_MAPPER.writeValueAsString(object);
        } catch (Exception ex) {
            payloadJson = null;
            logger.error("{} - Exception converting Object to Json {}", "SYS_UNSUP_FUNC", object);
            logger.error(ex.getMessage(), ex);
        }
        return payloadJson;
    }

    /**
     * Converts JSON string to Object of type specified as argument.
     * <p>
     * Example,
     * <pre><code>
     * 	// Create JSON String
     * 	String payloadJson = "{ 'name' : 'John', 'surname' : 'Smith' }";
     *
     * 	// Convert JSON String to POJO of type User
     * 	User user = JSONHandler.<b>fromJson</b>(payloadJson, User.class);
     * </code></pre>
     *
     * @param <T>         the generic type
     * @param payloadJson the JSON string data to be transformed to POJO
     * @param type        the type of object to be returned
     * @return the transformed object of type T, or null in case of exception
     */
    public static <T> T fromJson(final String payloadJson, final Class<T> type) {
        T payloadObj = null;
        try {
            payloadObj = OBJ_MAPPER.readValue(payloadJson, type);
        } catch (Exception ex) {
            logger.error("{} - Exception converting Json to Object of type {} - {}", "SYS_UNSUP_FUNC", type, payloadJson);
            logger.error(ex.getMessage(), ex);
        }
        return payloadObj;
    }

    /**
     * Converts JSON string to Object of type specified as argument. This is useful when type refers to generic, e.g. User&lt;Guest&gt; (instead of simple User.class).
     * <p>
     * Example,
     * <pre><code>
     * 	// Create JSON String
     * 	String strJson = "{ 'name' : 'John', 'surname' : 'Smith' }";
     *
     * 	// Convert JSON String to POJO of type User&lt;Guest&gt;
     * 	TypeReference&lt;User&lt;Guest&gt;&gt; ref = new TypeReference&lt;User&lt;Guest&gt;&gt;() {};
     * 	User&lt;Guest&gt; guestUser = JSONHandler.<b>fromJson</b>(strJson, ref);
     * </code></pre>
     *
     * @param <T>         the generic type
     * @param strJson the JSON string data to be transformed to POJO
     * @param ref         the type of object to be returned
     * @return the transformed object of type T
     */
    public static <T> T fromJson(final String strJson, final TypeReference<T> ref) {
        T payloadObj = null;
        try {
            payloadObj = OBJ_MAPPER.readValue(strJson, ref);
        } catch (Exception ex) {
            logger.error("{} - Exception converting Json to Object of type {} - {}", "SYS_UNSUP_FUNC", ref.getClass().getComponentType(), strJson);
            logger.error(ex.getMessage(), ex);
        }
        return payloadObj;
    }

    /**
     * Converts JSON string to {@link JsonNode}.
     * Example,
     * <pre><code>
     * 	// Create JSON String
     * 	String payloadJson = "{ 'name' : 'John', 'surname' : 'Smith' }";
     *
     * 	// Convert JSON String to JsonNode
     * 	JsonNode guestUser = JSONHandler.<b>fromString</b>(jsonString);
     * </code></pre>
     *
     * @param jsonString the json string
     * @return {@link JsonNode} the json node
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static JsonNode fromString(final String jsonString) throws IOException {
        return OBJ_MAPPER.readTree(jsonString);
    }

    /**
     * Return OBJ_MAPPER object instance.
     *
     * @return the object mapper
     */
    public static ObjectMapper getObjectMapper() {
        return OBJ_MAPPER;
    }

    /**
     * Converts ResultSet into array of JSONObjects
     *
     * @param rs the resultset
     * @return List of JSONObjects
     * @throws SQLException the sql exception
     */
    @SuppressWarnings("unchecked")
    public List<ObjectNode> resultSetToJson(ResultSet rs) throws SQLException {
        List<ObjectNode> jsonArray = new ArrayList<>();
        while (rs.next()) {
            ObjectNode object = OBJ_MAPPER.createObjectNode();
            int noOfColumns = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= noOfColumns; i++) {
                if (rs.getObject(i) != null && rs.getObject(i) != EMPTY_STRING) {
                    if (rs.getMetaData().getColumnType(i) == 93)
                        object.put(rs.getMetaData().getColumnLabel(i),
                                DateTimeUtil.timestampToString(new Date(rs.getTimestamp(i).getTime())));
                    else
                        object.set(rs.getMetaData().getColumnLabel(i),
                                OBJ_MAPPER.convertValue(rs.getObject(i), JsonNode.class));
                }
            }
            jsonArray.add(object);
        }
        return jsonArray;
    }

    /**
     * Creates an {@link ObjectNode} instance
     *
     * @return the object node
     */
    public static ObjectNode createNode() {
        return OBJ_MAPPER.createObjectNode();
    }

    /**
     * Creates an {@link ArrayNode} instance
     *
     * @return the array node
     */
    public static ArrayNode createArray() {
        return OBJ_MAPPER.createArrayNode();
    }

    /**
     * Creates an {@link ObjectNode} from object.Make sure the object is POJO
     *
     * @param obj the obj
     * @return the object node
     */
    public static ObjectNode readPojoToNode(Object obj) {
        return OBJ_MAPPER.valueToTree(obj);
    }

    /**
     * Creates a {@link TreeNode} from object.Make sure the object is a POJO
     *
     * @param obj the obj
     * @return the tree node
     */
    public static TreeNode readPojoToTreeNode(Object obj) {
        return OBJ_MAPPER.valueToTree(obj);
    }


}
