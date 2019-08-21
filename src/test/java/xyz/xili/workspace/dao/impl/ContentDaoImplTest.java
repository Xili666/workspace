package xyz.xili.workspace.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.xili.workspace.bean.Content;
import xyz.xili.workspace.dao.ContentDao;

import javax.annotation.Resource;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ContentDaoImplTest {

    @Resource
    private ContentDao contentDao;

    @Test
    public void testInsert() {
        Content content = new Content("qwertyuiop;.,mnbvcdfhjkl");
        content.setBelongDate(LocalDate.now());
        contentDao.insert(content);
    }

    @Test
    public void testQuery() {
        Content content = contentDao.query(1);
        System.out.println(content);
    }

    @Test
    public void testUpdate() {
        Content content = contentDao.query(1);
        content.setContent("这个我改过了, 你们都走开!");
        content.setModifyBy(121L);
        boolean update = contentDao.update(content);
        System.out.println(update);
    }

    @Test
    public void testDelete() {
        contentDao.delete(1L);
    }


    @Test
    public void testQueryObjectList() {
        List<Content> contents = contentDao.queryObjectListByFields(new String[]{"belongDate"}, new Object[]{LocalDate.now()});
        for (Content content : contents) {
            System.out.println(content);
        }

    }

    @Test
    public void testQueryObjectList2() {
        List<Content> contents = contentDao.queryObjectList("select * from t_content", new Object[]{});
        for (Content content : contents) {
            System.out.println(content);
        }

    }

    @Test
    public void testQueryObjectListByWhere() {
        List<Content> contents = contentDao.queryObjectListByWhere("content like ?", new Object[]{"%1%"});
        for (Content content : contents) {
            System.out.println(content);
        }

    }
}