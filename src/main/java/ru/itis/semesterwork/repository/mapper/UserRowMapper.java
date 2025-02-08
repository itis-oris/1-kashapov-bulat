package ru.itis.semesterwork.repository.mapper;

import ru.itis.semesterwork.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                    .id(rs.getInt("id"))
                    .username(rs.getString("username"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .skillpoints(rs.getInt("skillpoints"))
                    .avg_rating(rs.getFloat("avg_rating"))
                    .avatar(rs.getString("avatar"))
                    .session_id(rs.getString("session_id"))
                    .remember_id(rs.getString("remember_id"))
                .description(rs.getString("description")).build();


    }
}
