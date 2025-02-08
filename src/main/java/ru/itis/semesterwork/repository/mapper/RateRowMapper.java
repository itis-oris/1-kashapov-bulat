package ru.itis.semesterwork.repository.mapper;

import ru.itis.semesterwork.entity.Rate;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RateRowMapper implements RowMapper<Rate> {
    @Override
    public Rate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Rate.builder()
                .id(rs.getInt("id"))
                .skill(Skill.builder().id(rs.getInt("user_skill_id")).build())
                .user(User.builder().id(rs.getInt("user_id")).build())
                .rate(rs.getInt("rate"))
                .message(rs.getString("message")).build();
    }
}
