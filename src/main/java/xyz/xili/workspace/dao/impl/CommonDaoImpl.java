package xyz.xili.workspace.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import xyz.xili.workspace.bean.BaseBean;
import xyz.xili.workspace.dao.CommonDao;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public abstract class CommonDaoImpl<T extends BaseBean> implements CommonDao<T> {

    @Resource
    protected JdbcTemplate jdbcTemplate;

    @Resource
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    abstract String getTableName();

    abstract Class<T> getBeanClass();

    abstract String[] getCanUpdateFields();

    @Override
    public boolean insert(T bean) {
        bean.setCreateOn(LocalDateTime.now());
        bean.setModifyOn(LocalDateTime.now());
        bean.setRowVersion(1L);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(getTableName()).usingGeneratedKeyColumns("id");
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(bean);
        Number key = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        bean.setId(key.longValue());
        return true;
    }

    @Override
    public T query(long id) {
        String sql = "select * from " + getTableName() + " where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(getBeanClass()), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean update(T bean) {
        String sql = "update " + getTableName() + " set rowversion = rowversion + 1, modifyon = current_timestamp, modifyby = :modifyBy "
                + getUpdateFieldSql() + " where id = :id and rowversion = :rowVersion";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(bean);
        return namedParameterJdbcTemplate.update(sql, parameterSource) > 0;
    }

    @Override
    public boolean delete(long id) {
        String sql = "delete from " + getTableName() + " where id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private String getUpdateFieldSql() {
        StringBuilder sql = new StringBuilder();
        for (String field : getCanUpdateFields()) {
            sql.append(", ").append(field).append(" = :").append(field);
        }
        return sql.toString();
    }
}
