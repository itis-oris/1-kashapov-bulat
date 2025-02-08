package ru.itis.semesterwork.repository;

import lombok.AllArgsConstructor;
import ru.itis.semesterwork.entity.Rate;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.repository.mapper.RowMapper;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class RateRepository implements Repository<Rate>{
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Rate> rowMapper;
    private final static String GET_BY_ATTRIBUTE = "select * from rate where ";
    private final static String INSERT = "insert into rate (user_skill_id, user_id, rate, message) values (?, ?, ?, ?)";

    @Override
    public List<Rate> findAll() {
        return List.of();
    }

    @Override
    public List<Rate> findAll(int offset, int limit) {
        return List.of();
    }

    @Override
    public Optional<Rate> findByAttribute(String attribute, Object value) {
        return Optional.empty();
    }

    @Override
    public boolean update(Rate object) {
        return false;
    }

    @Override
    public boolean insert(Rate object) {
        Skill skill = object.getSkill();
        User user = object.getUser();
        Integer rate = object.getRate();
        String message = object.getMessage();
        if (skill == null || user == null || rate == null || message == null) {
            return false;
        }
        return jdbcTemplate.update(INSERT, skill.getId(), user.getId(), rate, message) == 1;
    }
    public Optional<Rate> findByUserAndSkill(User user, Skill skill) {
        String sql = GET_BY_ATTRIBUTE + "user_id = ? and user_skill_id = ?";
        return OptionalSingleResult.optionalSingleResult(jdbcTemplate.query(sql, rowMapper, user.getId(), skill.getId()));
    }
    public List<Rate> findAllByAttribute(String attribute, Object value) {
        String sql = GET_BY_ATTRIBUTE + attribute + " = ?";
        return jdbcTemplate.query(sql, rowMapper, value);
    }
}
