package ru.itis.semesterwork.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.repository.mapper.RowMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepositoryImpl implements Repository<User> {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper;


    private final static String GET_ALL = "select * from users";
    private final static String GET_ALL_PAGEABLE = "select * from users offset ? limit ?";
    private final static String GET_BY_ATTRIBUTE = "select * from users where ";
    private final static String UPDATE = "update users set ";
    private final static String INSERT = "insert into users (username, email, password";
    private final static String ADD_SKILLPOINT = "update users set skillpoints = ? where id = ";


    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(GET_ALL, userRowMapper);
    }

    @Override
    public List<User> findAll(int offset, int limit) {
        return jdbcTemplate.query(GET_ALL_PAGEABLE, userRowMapper, offset, limit);
    }

    @Override
    public Optional<User> findByAttribute(String attribute, Object value) {
        String sql = GET_BY_ATTRIBUTE + attribute + " = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, value);
        return OptionalSingleResult.optionalSingleResult(users);
    }
    @Override
    public boolean update(User user) {
        List<Object> parameters = new ArrayList<>();
        if (user.getUsername() != null) {
            StringBuilder sql = new StringBuilder(UPDATE);
            boolean flag = false;
            for (Field field: user.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (field.get(user) != null && !field.getName().equals("username")
                    && !field.getName().equals("id")) {
                        if (flag) {
                            sql.append(", ");
                        }
                        sql.append(field.getName()).append(" = ?");
                        parameters.add(field.get(user));
                        flag = true;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            if (flag) {
                sql.append(" where username = ?;");
                parameters.add(user.getUsername());
                return jdbcTemplate.update(sql.toString(), parameters.toArray()) > 0;
            }
        }
        return false;
    }
    public boolean addSkillPoint(User user, int skillPoint) {
        String sql = ADD_SKILLPOINT + user.getId();
        return jdbcTemplate.update(sql, skillPoint) > 0;
    }
    @Override
    public boolean insert(User user) {
        String sql = INSERT;
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        String description = user.getDescription();
        if (username == null || password == null || email == null) {
            return false;
        }
        if (description != null && !description.isEmpty()) {
            sql += ", description) values (?, ?, ?, ?)";
            jdbcTemplate.update(sql, username, email, password, description);
        } else {
            sql += ") values (?, ?, ?)";
            jdbcTemplate.update(sql, username, email, password);
        }

        return true;
    }
    public boolean updateAttribute(User user, String attribute, Object value) {
        String sql = UPDATE + attribute + " = ? where id = " + user.getId();
        return jdbcTemplate.update(sql, value) > 0;
    }
}
