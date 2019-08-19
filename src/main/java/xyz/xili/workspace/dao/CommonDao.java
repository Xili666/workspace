package xyz.xili.workspace.dao;

import xyz.xili.workspace.bean.BaseBean;

public interface CommonDao<T extends BaseBean> {
    boolean insert(T bean);

    T query(long id);

    boolean update(T bean);

    boolean delete(long id);
}
