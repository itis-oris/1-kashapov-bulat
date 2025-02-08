package ru.itis.semesterwork.repository.mapper;

import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillRowMapper implements RowMapper<Skill> {
    @Override
    public Skill mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Skill.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .category(rs.getString("category"))
                .user(User.builder().id(rs.getInt("user_id")).build())
                .description(rs.getString("description"))
                .rating(rs.getFloat("rating")).build();
    }
}
