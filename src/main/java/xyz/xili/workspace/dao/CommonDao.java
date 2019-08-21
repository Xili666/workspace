package xyz.xili.workspace.dao;

import xyz.xili.workspace.bean.BaseBean;

import java.util.List;

public interface CommonDao<T extends BaseBean> {
    boolean insert(T bean);

    T query(long id);

    T queryObjectByFields(String[] fields, Object[] params);

    List<T> queryObjectListByFields(String[] fields, Object[] params);

    List<T> queryObjectList(String sql, Object[] params);

    T queryObjectByWhere(String where, Object[] params);

    List<T> queryObjectListByWhere(String where, Object[] params);

    boolean update(T bean);

    boolean delete(long id);

    boolean delete(T bean);

    boolean insertOrUpdate(T bean);
}
