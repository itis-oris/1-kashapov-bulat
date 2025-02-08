package ru.itis.semesterwork.repository.mapper;

import ru.itis.semesterwork.entity.Lesson;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonRowMapper implements RowMapper<Lesson> {
    @Override
    public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {
       return Lesson.builder()
               .id(rs.getInt("id"))
               .status(rs.getString("status"))
               .teacher(User.builder().id(rs.getInt("teacher_id")).build())
               .student(User.builder().id(rs.getInt("student_id")).build())
               .teacherSkill(Skill.builder().id(rs.getInt("teacher_skill_id")).build())
               .build();
    }
}
