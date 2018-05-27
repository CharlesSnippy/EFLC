package com.edu.repositories;

import com.edu.mvc.models.Criterion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Deprecated
@Repository
public class CriterionRepository {

    @Autowired
    DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    static final Logger logger = LogManager.getLogger(CriterionRepository.class);
    private static String TABLE_NAME = "CRITERIA";

    @PostConstruct
    public void init() {
        logger.info("init()");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    RowMapper<Criterion> pageRowMapper = new RowMapper<Criterion>() {
        @Override
        public Criterion mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            logger.info("pageRowMapper");
            Criterion criterion = new Criterion();
            criterion.setCriteriaId(resultSet.getInt("ID"));
            criterion.setName(resultSet.getString("NAME"));
            criterion.setShortDescription(resultSet.getString("SHORTDESC"));
            criterion.setLongDescription(resultSet.getString("LONGDESC"));
            criterion.setLongDescription(resultSet.getString("LONGDESC"));
            criterion.setDictionary(resultSet.getString("DICTIONARY"));
            return criterion;
        }
    };

    public void create(Criterion criterion) {
        logger.info("create({})", criterion.getName());
        String SQL_CREATE = "INSERT INTO " + TABLE_NAME + " (NAME, SHORTDESC, LONGDESC, DICT) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, criterion.getName());
                preparedStatement.setString(2, criterion.getShortDescription());
                preparedStatement.setString(3, criterion.getLongDescription());
                preparedStatement.setString(4, criterion.getDictionary());
                return preparedStatement;
            }
        }, keyHolder);
        criterion.setCriteriaId((Integer) keyHolder.getKeys().get("id"));
    }

    public Criterion getById(int criteriaId) {
        logger.info("getById({})", criteriaId);
        String SQL_GET_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
        return jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[]{
                criteriaId
        }, pageRowMapper);
    }

    public List<Criterion> getAll() {
        logger.info("getAll()");
        String SQL_GET_ALL = "SELECT * FROM " + TABLE_NAME + " ORDER BY ID";
        return jdbcTemplate.query(SQL_GET_ALL, pageRowMapper);
    }

    public void update(Criterion criterion) {
        logger.info("update({})", criterion.getName());
        String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET NAME = ?, SHORTDESC = ?, LONGDESC = ?, DICT = ? WHERE ID = ?";
        jdbcTemplate.update(SQL_UPDATE,
                criterion.getName(),
                criterion.getShortDescription(),
                criterion.getLongDescription(),
                criterion.getDictionary());
    }

    public void delete(int criteriaId) {
        logger.info("delete({})", criteriaId);
        String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE ID = ?";
        jdbcTemplate.update(SQL_DELETE,
                criteriaId);
    }

}
