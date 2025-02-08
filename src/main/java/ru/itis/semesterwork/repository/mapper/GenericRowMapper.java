package ru.itis.semesterwork.repository.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericRowMapper<T> implements RowMapper<T> {
    protected Class<T> entityClass;
    public GenericRowMapper(Class<T> clazz) {
        this.entityClass = clazz;
        System.out.println(clazz.getName());
    }
    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            T result = entityClass.getDeclaredConstructor().newInstance();
            for (Field field : entityClass.getDeclaredFields()) {
                String fieldName = field.getName();
                if (rs.getObject(fieldName) != null) {
                    String setter = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method method = result.getClass().getMethod(setter, field.getType());
                    method.invoke(result, rs.getObject(fieldName));
                }
            }
            return result;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
