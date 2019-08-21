package xyz.xili.workspace.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import xyz.xili.workspace.bean.Content;
import xyz.xili.workspace.controller.exception.BOException;
import xyz.xili.workspace.service.ContentService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/content")
public class ContentController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

    @Resource
    private ContentService contentService;


    @GetMapping("/getContent/{date}")
    public Map<String, Object> getContent(@PathVariable("date") String date) {
        Map<String, Object> resultMap = getResultMap();
        if (StringUtils.isNotBlank(date)) {
            LocalDate parseDate;
            try {
                parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new BOException(e.getMessage());
            }
            if (parseDate != null) {
                Content content = contentService.getContent(parseDate);
                if (content != null) {
                    resultMap.put("content", content.getContent());
                } else {
                    resultMap.put("content", "");
                }
            }
        }
        return resultMap;
    }

    @PostMapping("/setContent")
    public Map<String, Object> setContent(String date, String content) {
        LocalDate parseDate;
        try {
            parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BOException(e.getMessage());
        }
        try {
            contentService.setContent(content, parseDate);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BOException(e.getMessage());
        }
        return getResultMap();
    }

}
