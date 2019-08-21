package xyz.xili.workspace.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.xili.workspace.bean.Content;
import xyz.xili.workspace.service.ContentService;

import javax.annotation.Resource;

import java.time.LocalDate;
import java.time.Month;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ContentServiceImplTest {

    @Resource
    private ContentService contentService;

    @Test
    public void getContent() {
        LocalDate localDate = LocalDate.of(2019, Month.AUGUST, 16);
        System.out.println(localDate.toString());
        Content content = contentService.getContent(localDate);
        System.out.println(content);
    }
}