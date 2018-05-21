package com.edu.repositories;

import com.edu.mvc.models.Criteria;
import org.jsoup.Jsoup;
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

@Repository
public class CriteriaRepository {

    @Autowired
    DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private static String TABLE_NAME = "CRITERIA";

    @PostConstruct
    public void init() {
        System.out.println("CriteriaRepository postConstruct is called. datasource = " + dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    RowMapper<Criteria> pageRowMapper = new RowMapper<Criteria>() {
        @Override
        public Criteria mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Criteria criteria = new Criteria();
            criteria.setCriteriaId(resultSet.getInt("ID"));
            criteria.setName(resultSet.getString("NAME"));
            criteria.setShortDescription(resultSet.getString("SHORTDESC"));
            criteria.setLongDescription(resultSet.getString("LONGDESC"));
            criteria.setLongDescription(resultSet.getString("LONGDESC"));
            criteria.setDictionary(resultSet.getString("DICTIONARY"));
            System.out.println("Getting " + Criteria.class.getName() + ": " + criteria.toString());
            return criteria;
        }
    };

    public void create(Criteria criteria) {
        String SQL_CREATE = "INSERT INTO " + TABLE_NAME + " (NAME, SHORTDESC, LONGDESC, DICTIONARY) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, criteria.getName());
                preparedStatement.setString(2, criteria.getShortDescription());
                preparedStatement.setString(3, criteria.getLongDescription());
                preparedStatement.setString(4, criteria.getDictionary());
                return preparedStatement;
            }
        }, keyHolder);
        criteria.setCriteriaId((Integer) keyHolder.getKeys().get("id"));
    }

    public Criteria getById(int criteriaId) {
        String SQL_GET_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
        return jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[]{
                criteriaId
        }, pageRowMapper);
    }

    public List<Criteria> getAll() {
        String SQL_GET_ALL = "SELECT * FROM " + TABLE_NAME + " ORDER BY ID";
        return jdbcTemplate.query(SQL_GET_ALL, pageRowMapper);
    }

    public void update(Criteria criteria) {
        String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET NAME = ?, SHORTDESC = ?, LONGDESC = ?, DICTIONARY = ? WHERE ID = ?";
        jdbcTemplate.update(SQL_UPDATE,
                criteria.getName(),
                criteria.getShortDescription(),
                criteria.getLongDescription(),
                criteria.getDictionary());
    }

    public void delete(int criteriaId) {
        String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE ID = ?";
        jdbcTemplate.update(SQL_DELETE,
                criteriaId);
    }

}
