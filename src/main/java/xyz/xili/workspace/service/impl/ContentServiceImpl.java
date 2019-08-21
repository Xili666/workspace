package xyz.xili.workspace.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xyz.xili.workspace.bean.Content;
import xyz.xili.workspace.dao.ContentDao;
import xyz.xili.workspace.service.ContentService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Resource
    private ContentDao contentDao;

    @Override
    public Content getContent(LocalDate parseDate) {
        List<Content> contents = contentDao.queryObjectListByWhere("belongDate = ?", new Object[]{parseDate});
        if (!contents.isEmpty()) {
            return contents.get(0);
        }
        return null;
    }

    @Override
    public void setContent(String content, LocalDate belongDate) {
        Content contentObj = contentDao.queryObjectByFields(new String[]{"belongdate"}, new Object[]{belongDate});
        if (contentObj == null) {
            contentObj = new Content();
            contentObj.setContent(content);
            contentObj.setBelongDate(belongDate);
            contentDao.insert(contentObj);
        } else {
            contentObj.setContent(content);
            contentDao.update(contentObj);
        }
    }
}
