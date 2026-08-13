package com.github.spranshu1.common.util.test.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.spranshu1.common.util.json.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * The type Json parser test.
 */
public class JsonParserTest {

    private static final String SAMPLE_JSON = "{\"name\":\"shivansh\",\"email\":\"shiv@domain.com\",\"age\":22}";

    /**
     * Sample test.
     */
    @Test
    public void sampleTest(){
        JsonParser<JsonNode> parser = new JsonParser.JsonParserBuilder<>(JsonNode.class).build();
        try {
            JsonNode node=parser.readJsonNodeTree("{\"c\":12,\"d\":23}");
            Assert.assertEquals("Incorrect",12,node.get("c").intValue());
        } catch (IOException e) {
            Assert.fail("Json parsing failed");
        }
    }


    /**
     * Serializer test.
     */
    @Test
    public void serializerTest(){
        SimpleModule module = new SimpleModule("MyModule", new Version(1, 0, 0, null, "com.github.spranshu1.common.util",
                "common-util"));
        JsonParser<Employee> parser = new JsonParser.JsonParserBuilder<Employee>(module)
                .withSerializer(Employee.class, new EmpSerializer(Employee.class))
                .build();

        ObjectNode obj = parser.readPojoToNode(new Employee("Ravi", "ravi@domain.com", 21));

        Assert.assertEquals("Incorrect",obj.get("email").asText(),"ravi@domain.com");
    }

    /**
     * De-serializer test.
     */
    @Test
    public void deSerializerTest() throws IOException {
        JsonParser<Employee> parser = new JsonParser.JsonParserBuilder<>(Employee.class)
                .withDeSerializer(Employee.class, new EmpDeSerializer(Employee.class))
                .build();

        Employee obj = parser.getJavaObject(SAMPLE_JSON);

        Assert.assertTrue("shiv@domain.com".equals(obj.email));
    }


    /**
     * The type Emp de serializer.
     */
    class EmpDeSerializer extends StdDeserializer<Employee>{

        /**
         * Instantiates a new Emp de serializer.
         *
         * @param vc the vc
         */
        protected EmpDeSerializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Employee deserialize(com.fasterxml.jackson.core.JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            int age = (Integer) node.get("age").numberValue();
            String name = node.get("name").asText();
            String email = node.get("email").asText();

            return new Employee(name,email,age);
        }
    }


    /**
     * The type Emp serializer.
     */
    class EmpSerializer extends StdSerializer<Employee>{

        /**
         * Instantiates a new Emp serializer.
         *
         * @param t the t
         */
        protected EmpSerializer(Class<Employee> t) {
            super(t);
        }

        @Override
        public void serialize(Employee value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeNumberField("age", value.age);
            gen.writeStringField("name", value.name);
            gen.writeStringField("email", value.email);
            gen.writeEndObject();
        }
    }

    /**
     * The type Employee.
     */
    class Employee{
        /**
         * The Name.
         */
        String name;
        /**
         * The Email.
         */
        String email;
        /**
         * The Age.
         */
        int age;

        /**
         * Instantiates a new Employee.
         *
         * @param name  the name
         * @param email the email
         * @param age   the age
         */
        public Employee(String name, String email, int age) {
            this.name = name;
            this.email = email;
            this.age = age;
        }

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Sets name.
         *
         * @param name the name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Gets email.
         *
         * @return the email
         */
        public String getEmail() {
            return email;
        }

        /**
         * Sets email.
         *
         * @param email the email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * Gets age.
         *
         * @return the age
         */
        public int getAge() {
            return age;
        }

        /**
         * Sets age.
         *
         * @param age the age
         */
        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return name+","+email+","+age;
        }
    }
}
