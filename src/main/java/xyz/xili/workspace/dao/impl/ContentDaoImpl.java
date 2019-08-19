package xyz.xili.workspace.dao.impl;

import org.springframework.stereotype.Repository;
import xyz.xili.workspace.bean.Content;
import xyz.xili.workspace.dao.ContentDao;

@Repository
public class ContentDaoImpl extends CommonDaoImpl<Content> implements ContentDao {
    @Override
    String getTableName() {
        return Content.TABLE_NAME;
    }

    @Override
    Class<Content> getBeanClass() {
        return Content.class;
    }

    @Override
    String[] getCanUpdateFields() {
        return new String[]{"content"};
    }
}
