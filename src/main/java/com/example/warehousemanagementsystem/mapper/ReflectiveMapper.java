package com.example.warehousemanagementsystem.mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ReflectiveMapper {

    public static <T> T map(ResultSet rs, Class<T> clazz) throws SQLException {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            Map<String, Object> columnValues = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnLabel = metaData.getColumnLabel(i);
                Object value = rs.getObject(i);
                columnValues.put(columnLabel.toLowerCase(), value);
            }

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String expectedColumn = toSnakeCase(field.getName()).toLowerCase();

                if (columnValues.containsKey(expectedColumn)) {
                    Object value = columnValues.get(expectedColumn);
                    try {
                        if (field.getType().isEnum() && value instanceof String) {
                            Object enumValue = Enum.valueOf((Class<Enum>) field.getType(), ((String) value).toUpperCase());
                            field.set(instance, enumValue);
                        } else if (value != null && field.getType().isAssignableFrom(value.getClass())) {
                            field.set(instance, value);
                        } else if (value != null) {
                            Object converted = convertType(value, field.getType());
                            field.set(instance, converted);
                        }
                    } catch (Exception fieldEx) {
                        System.err.printf(
                                "Ошибка при маппинге поля '%s' (тип: %s), значение из БД: %s (тип: %s)%n",
                                field.getName(),
                                field.getType().getSimpleName(),
                                value,
                                value != null ? value.getClass().getSimpleName() : "null"
                        );
                        throw fieldEx;
                    }
                }
            }

            return instance;

        } catch (Exception e) {
            throw new SQLException("Ошибка маппинга через рефлексию для " + clazz.getSimpleName(), e);
        }
    }

    private static Object convertType(Object value, Class<?> targetType) {
        if (targetType == long.class || targetType == Long.class) {
            return ((Number) value).longValue();
        } else if (targetType == int.class || targetType == Integer.class) {
            return ((Number) value).intValue();
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return value instanceof Boolean ? value : Boolean.parseBoolean(value.toString());
        } else if (targetType == double.class || targetType == Double.class) {
            return ((Number) value).doubleValue();
        } else if (targetType == java.time.OffsetDateTime.class && value instanceof java.sql.Timestamp) {
            java.sql.Timestamp ts = (java.sql.Timestamp) value;
            return ts.toInstant().atOffset(java.time.ZoneOffset.UTC);
        }
        return value;
    }


    private static String toSnakeCase(String camelCase) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append('_').append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
