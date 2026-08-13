package kg.nurtelecom.registration.api.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.lang.reflect.Field;
import java.util.Arrays;

public class TableInspector {
    public static String getStringJsonArray(Class<?> clazz, ObjectMapper mapper) {
        ArrayNode jsonArray = mapper.createArrayNode();

        for (Field field : clazz.getDeclaredFields()) {
            String name = field.getName();
            String json = getJsonProperty(field);
            String column = getColumn(field);
            String type = field.getType().getSimpleName();
            String relation = getRelation(field);

            ObjectNode fieldNode = mapper.createObjectNode();
            fieldNode.put("name", name);
            fieldNode.put("json", json);
            fieldNode.put("column", column);
            fieldNode.put("type", type);
            if (relation != null) {
                fieldNode.put("relation", relation);
            }
            jsonArray.add(fieldNode);
        }

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonArray);
        } catch (JsonProcessingException jpe) {
            return "{}";
        }
    }

    public static String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            return table.name().isEmpty() ? toSnakeCase(clazz.getSimpleName()) : table.name();
        }
        return toSnakeCase(clazz.getSimpleName());
    }

    private static String getJsonProperty(Field field) {
        if (field.isAnnotationPresent(JsonProperty.class)) {
            String value = field.getAnnotation(JsonProperty.class).value();
            return value.isEmpty() ? field.getName() : value;
        }
        return field.getName();
    }

    private static String getColumn(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            String value = field.getAnnotation(Column.class).name();
            return value.isEmpty() ? toSnakeCase(field.getName()) : value;
        }
        if (field.isAnnotationPresent(JoinColumn.class)) {
            return field.getAnnotation(JoinColumn.class).name();
        }
        if (field.isAnnotationPresent(JoinColumns.class)) {
            JoinColumns joinColumns = field.getAnnotation(JoinColumns.class);
            return Arrays.stream(joinColumns.value())
                    .map(JoinColumn::name)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse(null);
        }
        if (field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class)) {
            return toSnakeCase(field.getName()) + "_id";
        }
        return toSnakeCase(field.getName());
    }

    private static String getRelation(Field field) {
        if (field.isAnnotationPresent(OneToOne.class)) {
            return "OneToOne";
        }
        if (field.isAnnotationPresent(OneToMany.class)) {
            return "OneToMany";
        }
        if (field.isAnnotationPresent(ManyToOne.class)) {
            return "ManyToOne";
        }
        if (field.isAnnotationPresent(ManyToMany.class)) {
            return "ManyToMany";
        }
        return null;
    }

    private static String toSnakeCase(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
