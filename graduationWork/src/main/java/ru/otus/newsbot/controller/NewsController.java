package ru.otus.newsbot.controller;

import ru.otus.newsbot.domain.Status;
import ru.otus.newsbot.domain.dto.NewsDto;

import java.util.List;

public interface NewsController {
    List<Status> createNews(NewsDto newsDto);
}
