package xyz.xili.workspace.dao;

import xyz.xili.workspace.bean.BaseBean;

import java.util.List;

public interface CommonDao<T extends BaseBean> {
    boolean insert(T bean);

    T query(long id);

    List<T> queryObjectList(String[] fields, Object[] params);

    List<T> queryObjectList(String sql, Object[] params);

    List<T> queryObjectListByWhere(String where, Object[] params);

    boolean update(T bean);

    boolean delete(long id);

    boolean delete(T bean);
}
