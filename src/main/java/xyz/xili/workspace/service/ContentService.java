package xyz.xili.workspace.service;

import xyz.xili.workspace.bean.Content;

import java.time.LocalDate;

public interface ContentService {
    Content getContent(LocalDate parseDate);

    void setContent(String content, LocalDate parseDate);
}
