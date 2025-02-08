package ru.itis.semesterwork.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<String>{
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("category");
    }
}
