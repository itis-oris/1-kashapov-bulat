package ru.itis.semesterwork.repository;

import lombok.AllArgsConstructor;
import ru.itis.semesterwork.entity.Lesson;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.repository.mapper.RowMapper;

import java.util.List;
import java.util.Optional;

import static ru.itis.semesterwork.repository.OptionalSingleResult.optionalSingleResult;

@AllArgsConstructor
public class LessonRepository implements Repository<Lesson> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Lesson> lessonRowMapper;
    private final static String GET_ALL = "select * from lesson";
    private final static String GET_BY_ATTRIBUTE = "select * from lesson where ";
    private final static String INSERT = "insert into lesson (teacher_id, student_id, teacher_skill_id) values (?, ?, ?)";
    private final static String UPDATE = "update lesson set ";

    @Override
    public List<Lesson> findAll() {
        return List.of();
    }

    @Override
    public List<Lesson> findAll(int offset, int limit) {
        return List.of();
    }

    @Override
    public Optional<Lesson> findByAttribute(String attribute, Object value) {
        String sql = GET_BY_ATTRIBUTE + attribute + " = ?";
        return optionalSingleResult(jdbcTemplate.query(sql, lessonRowMapper, value));
    }
    public List<Lesson> findByUser(User user, int offset, int limit) {
        String sql = GET_ALL + " where teacher_id = ? or student_id = ? offset " + offset + " limit " + limit;
        return jdbcTemplate.query(sql, lessonRowMapper, user.getId(), user.getId());
    }
    public List<Lesson> findByTeacher(User user, int offset, int limit) {
        String sql = GET_ALL + " where teacher_id = ? offset " + offset + " limit " + limit;
        return jdbcTemplate.query(sql, lessonRowMapper, user.getId(), user.getId());
    }
    public List<Lesson> findByUser(User user) {
        String sql = GET_ALL + " where teacher_id = ? or student_id = ?";
        return jdbcTemplate.query(sql, lessonRowMapper, user.getId(), user.getId());
    }
    public List<Lesson> findByTeacher(User user) {
        String sql = GET_ALL + " where teacher_id = ?";
        return jdbcTemplate.query(sql, lessonRowMapper, user.getId(), user.getId());
    }
    public List<Lesson> findByStudent(User user, int offset, int limit) {
        String sql = GET_ALL + " where student_id = ? offset " + offset + " limit " + limit;
        return jdbcTemplate.query(sql, lessonRowMapper, user.getId(), user.getId());
    }
    public List<Lesson> findByStudent(User user) {
        String sql = GET_ALL + " where student_id = ?";
        return jdbcTemplate.query(sql, lessonRowMapper, user.getId(), user.getId());
    }
    public List<Lesson> findBySkillAndStudent(Skill skill, User student) {
        String sql = GET_ALL + " where teacher_skill_id = ? and student_id = ?";
        return jdbcTemplate.query(sql, lessonRowMapper, skill.getId(), student.getId());
    }

    @Override
    public boolean update(Lesson object) {
        return false;
    }
    public boolean updateAttribute(Integer id, String attribute, Object value) {
        String sql = UPDATE + attribute + " = ? where id = ?";
        return jdbcTemplate.update(sql, value, id) > 0;
    }

    @Override
    public boolean insert(Lesson object) {
        String sql = INSERT;
        User teacher = object.getTeacher();
        User student = object.getStudent();
        Skill skill = object.getTeacherSkill();
        if (teacher == null || student == null || skill == null) {
            return false;
        } else {
            jdbcTemplate.update(sql, teacher.getId(), student.getId(), skill.getId());
            return true;
        }
    }
}
