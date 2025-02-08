package ru.itis.semesterwork.repository.mapper;

import ru.itis.semesterwork.entity.Notification;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowMapper implements RowMapper<Notification>{
    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Notification.builder()
                .id(rs.getInt("id"))
                .from(User.builder().id(rs.getInt("from_id")).build())
                .to(User.builder().id(rs.getInt("to_id")).build())
                .skill(Skill.builder().id(rs.getInt("skill_id")).build())
                .type(rs.getString("type"))
                .message(rs.getString("message"))
                .status(rs.getString("status")).build();
    }
}
