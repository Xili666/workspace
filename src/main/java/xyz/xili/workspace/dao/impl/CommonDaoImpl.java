package xyz.xili.workspace.dao.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import xyz.xili.workspace.bean.BaseBean;
import xyz.xili.workspace.dao.CommonDao;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

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
    public T queryObjectByFields(String[] fields, Object[] params) {
        List<T> ts = queryObjectListByFields(fields, params);
        if (!ts.isEmpty()) {
            return ts.get(0);
        }
        return null;
    }

    @Override
    public List<T> queryObjectListByFields(String[] fields, Object[] params) {
        StringBuilder sql = new StringBuilder("select * from " + getTableName());
        if (ArrayUtils.isNotEmpty(fields) && ArrayUtils.isNotEmpty(params) && ArrayUtils.isSameLength(fields, params)) {
            sql.append(" where ");
            for (int i = 0, fieldsLength = fields.length; i < fieldsLength; i++) {
                if (i > 0) {
                    sql.append(" and").append(fields[i]).append(" = ?");
                } else {
                    sql.append(fields[i]).append(" = ?");
                }
            }
        }
        return queryObjectList(sql.toString(), params);
    }

    @Override
    public List<T> queryObjectList(String sql, Object[] params) {
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(getBeanClass()), params);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public T queryObjectByWhere(String where, Object[] params) {
        List<T> ts = queryObjectListByWhere(where, params);
        if (!ts.isEmpty()) {
            return ts.get(0);
        }
        return null;
    }

    @Override
    public List<T> queryObjectListByWhere(String where, Object[] params) {
        String sql = "select * from " + getTableName() + " where " + where;
        return queryObjectList(sql, params);
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

    @Override
    public boolean delete(T bean) {
        if (bean != null && bean.getId() != null) {
            return delete(bean.getId());
        } else {
            throw new NullPointerException("对象没有ID!");
        }
    }

    @Override
    public boolean insertOrUpdate(T bean) {
        boolean insert = insert(bean);
        if (!insert) {
            return update(bean);
        }
        return true;
    }

    private String getUpdateFieldSql() {
        StringBuilder sql = new StringBuilder();
        for (String field : getCanUpdateFields()) {
            sql.append(", ").append(field).append(" = :").append(field);
        }
        return sql.toString();
    }
}
