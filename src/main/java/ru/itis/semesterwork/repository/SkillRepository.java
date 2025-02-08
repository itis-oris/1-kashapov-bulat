package ru.itis.semesterwork.repository;

import lombok.AllArgsConstructor;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.repository.mapper.RowMapper;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SkillRepository implements Repository<Skill>{
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Skill> skillRowMapper;
    private RowMapper<String> categoryMapper;

    private final static String GET_BY_ATTRIBUTE = "select * from users_skill where ";
    private static final String GET_ALL_CATEGORIES = "select distinct category from users_skill";
    private final static String INSERT = "insert into users_skill (name, category, description, user_id) values (?, ?, ?, ?)";
    private final static String UPDATE = "update users_skill set ";
    private final static String END = "order by rating desc";
    @Override
    public List<Skill> findAll() {
        return List.of();
    }

    @Override
    public List<Skill> findAll(int offset, int limit) {
        return List.of();
    }

    @Override
    public Optional<Skill> findByAttribute(String attribute, Object value) {
        String sql = GET_BY_ATTRIBUTE + attribute + " = ?";
        return OptionalSingleResult.optionalSingleResult(jdbcTemplate.query(sql, skillRowMapper, value));
    }
    public List<String> getAllCategories() {
        return jdbcTemplate.query(GET_ALL_CATEGORIES, categoryMapper);
    }

    public List<Skill> findByUser(User user) {
        String sql = GET_BY_ATTRIBUTE + "user_id = ? " + END;
        return jdbcTemplate.query(sql, skillRowMapper, user.getId());
    }
    public List<Skill> findByUser(User user, int offset, int limit) {
        String sql = GET_BY_ATTRIBUTE + "user_id = ? " + END + " offset " + offset + " limit " + limit;
        return jdbcTemplate.query(sql, skillRowMapper, user.getId());
    }

    public Optional<Skill> findByUserAndName(User user, String name) {
        String sql = GET_BY_ATTRIBUTE + "user_id = ? and name = ?";
        return OptionalSingleResult.optionalSingleResult(jdbcTemplate.query(sql, skillRowMapper, user.getId(), name));
    }
    public boolean updateAttribute(Skill skill, String attribute, Object value) {
        String sql = UPDATE + attribute + " = ? where id = " + skill.getId();
        return jdbcTemplate.update(sql, value) > 0;
    }

    @Override
    public boolean update(Skill object) {
        return false;
    }

    @Override
    public boolean insert(Skill object) {
        String name = object.getName();
        String category = object.getCategory();
        String description = object.getDescription();
        Integer userId = object.getUser().getId();
        if (name == null || category == null || description == null) {
            return false;
        }
        return jdbcTemplate.update(INSERT, name, category, description, userId) == 1;
    }
}
