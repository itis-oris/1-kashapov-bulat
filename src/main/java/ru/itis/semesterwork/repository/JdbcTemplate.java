package ru.itis.semesterwork.repository;


import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.semesterwork.repository.mapper.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class JdbcTemplate {
    private DataSource dataSource;
    private final static Logger logger = LogManager.getLogger(JdbcTemplate.class);

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object ... objects) {
        ResultSet resultSet = null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParameters(preparedStatement, objects);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();

            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(rowMapper.mapRow(resultSet, 0));
            }
            logger.info(sql + ": successfully executed");
            return list;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error(e);
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public int update(String sql, Object... params) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(statement, params);

            logger.info(sql + ":update successfully executed");
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException("Error executing update: " + sql, e);
        }
    }
    private void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 1; i <= params.length; i++) {
                statement.setObject(i, params[i - 1]);
        }
    }
}
