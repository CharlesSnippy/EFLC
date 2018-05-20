package com.edu.repositories;

import com.edu.mvc.models.Site;
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
public class SiteRepository {

    @Autowired
    DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private static String TABLE_NAME = "SITE";

    @PostConstruct
    public void init() {
        System.out.println("SiteRepository postConstruct is called. datasource = " + dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    RowMapper<Site> siteRowMapper = new RowMapper<Site>() {
        @Override
        public Site mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Site site = new Site();
            site.setSiteId(resultSet.getInt("ID"));
            site.setUrl(resultSet.getString("URL"));
            System.out.println("Getting " + Site.class.getName() + ": " + site.toString());
            return site;
        }
    };

    public void create(Site site) {
        String SQL_CREATE = "INSERT INTO " + TABLE_NAME + " (URL) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, site.getUrl());
                return preparedStatement;
            }
        }, keyHolder);
        site.setSiteId((Integer) keyHolder.getKeys().get("id"));
    }

    public Site getById(int siteId) {
        String SQL_GET_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
        return jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[]{
                siteId
        }, siteRowMapper);
    }

    public List<Site> getAll() {
        String SQL_GET_ALL = "SELECT * FROM " + TABLE_NAME + " ORDER BY ID";
        return jdbcTemplate.query(SQL_GET_ALL, siteRowMapper);
    }

    public void update(Site site) {
        String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET URL = ? WHERE ID = ?";
        jdbcTemplate.update(SQL_UPDATE,
                site.getUrl(),
                site.getSiteId());
    }

    public void delete(int siteId) {
        String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " CASCADE WHERE ID = ?";
        jdbcTemplate.update(SQL_DELETE,
                siteId);
    }

}
