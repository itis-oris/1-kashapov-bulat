package ru.itis.semesterwork.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
    public T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
