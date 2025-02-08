package ru.itis.semesterwork.repository;

import lombok.AllArgsConstructor;
import ru.itis.semesterwork.entity.Lesson;
import ru.itis.semesterwork.entity.Notification;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.repository.mapper.RowMapper;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class NotificationRepository implements Repository<Notification> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Notification> notificationRowMapper;
    private final static String GET_ALL = "select * from notification";
    private final static String GET_BY_ATTRIBUTE = "select * from notification where ";
    private final static String UPDATE = "update notification set ";
    private final static String INSERT = "insert into notification (from_id, to_id, type, skill_id) values (?, ?, ?, ?)";
    private final static String END = " order by date_time desc";
    @Override
    public List<Notification> findAll() {
        return jdbcTemplate.query(GET_ALL, notificationRowMapper);
    }

    @Override
    public List<Notification> findAll(int offset, int limit) {
        String sql = GET_ALL + "offset " + offset + " limit " + limit + END;
        return jdbcTemplate.query(sql, notificationRowMapper);
    }

    @Override
    public Optional<Notification> findByAttribute(String attribute, Object value) {
        String sql = GET_BY_ATTRIBUTE + attribute + " = ?";
        return OptionalSingleResult.optionalSingleResult(jdbcTemplate.query(sql, notificationRowMapper, value));
    }
    public List<Notification> findByUser(User user) {
        String sql = GET_BY_ATTRIBUTE + "to_id = ?";
        return jdbcTemplate.query(sql, notificationRowMapper, user.getId());
    }
    public List<Notification> findByUser(User user, int offset, int limit) {
        String sql = GET_BY_ATTRIBUTE + "to_id = ?" + END + " offset " + offset + " limit " + limit;
        return jdbcTemplate.query(sql, notificationRowMapper, user.getId());
    }
    public boolean update(Integer id, String attribute, Object value) {
        String sql = UPDATE + attribute + " = ? where id = ?";
        return jdbcTemplate.update(sql, value, id) > 0;
    }
    @Override
    public boolean update(Notification object) {
        return false;
    }

    @Override
    public boolean insert(Notification object) {
        String sql = INSERT;
        if (object.getMessage() != null) {
            sql = "insert into notification (from_id, to_id, type, skill_id, message) values (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, object.getFrom().getId(), object.getTo().getId(), object.getType(), object.getSkill().getId(), object.getMessage());
        } else {
            jdbcTemplate.update(sql, object.getFrom().getId(), object.getTo().getId(), object.getType(), object.getSkill().getId());

        }
        return true;
    }
}
