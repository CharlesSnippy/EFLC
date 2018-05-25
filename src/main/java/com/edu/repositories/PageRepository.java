package com.edu.repositories;

import com.edu.mvc.models.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class PageRepository {

    @Autowired
    DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    static final Logger logger = LogManager.getLogger(PageRepository.class);
    private static String TABLE_NAME = "PAGE";

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        logger.info("postConstruct is called. datasource = " + dataSource);
    }

    RowMapper<Page> pageRowMapper = new RowMapper<Page>() {
        @Override
        public Page mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Page page = new Page();
            page.setPageId(resultSet.getInt("ID"));
            page.setSiteId(resultSet.getInt("SITEID"));
            page.setUrl(resultSet.getString("URL"));
            page.setTitle(resultSet.getString("TITLE"));
            page.setDocument(Jsoup.parse(resultSet.getString("PAGEDOC")));
            logger.info("mapRow:" + page.getUrl());
            return page;
        }
    };

    public void create(Page page) {
        logger.info("create:" + page.getUrl());
        String SQL_CREATE = "INSERT INTO " + TABLE_NAME + " (SITEID, URL, TITLE, PAGEDOC) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, page.getSiteId());
                preparedStatement.setString(2, page.getUrl());
                preparedStatement.setString(3, page.getTitle());
                preparedStatement.setString(4, (page.getDocument() == null) ? "" : page.getDocument().toString());
                return preparedStatement;
            }
        }, keyHolder);
        page.setPageId((Integer) keyHolder.getKeys().get("id"));
    }

    public Page getById(int sitePageId) {
        logger.info("getById:" + sitePageId);
        String SQL_GET_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
        return jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[]{
                sitePageId
        }, pageRowMapper);
    }

    public List<Page> getAll() {
        logger.info("getAll:");
        String SQL_GET_ALL = "SELECT * FROM " + TABLE_NAME + " ORDER BY ID";
        return jdbcTemplate.query(SQL_GET_ALL, pageRowMapper);
    }

    public void update(Page page) {
        logger.info("update:" + page.getUrl());
        String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET SITEID = ?, URL = ?, TITLE = ?, PAGEDOC = ? WHERE ID = ?";
        jdbcTemplate.update(SQL_UPDATE,
                page.getSiteId(),
                page.getUrl(),
                page.getTitle(),
                (page.getDocument() == null) ? "" : page.getDocument().toString(),

                page.getPageId());
    }

    public void delete(int sitePageId) {
        logger.info("delete:" + sitePageId);
        String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE ID = ?";
        jdbcTemplate.update(SQL_DELETE,
                sitePageId);
    }


    public List<Page> getPagesBySiteId(int siteId) {
        logger.info("getPagesBySiteId:" + siteId);
        String SQL_GET_PAGES_BY_SITE_ID = "SELECT * FROM " + TABLE_NAME + " WHERE SITEID = ? ORDER BY TITLE";
        return jdbcTemplate.query(SQL_GET_PAGES_BY_SITE_ID, new Object[]{
                siteId
        }, pageRowMapper);
    }
}