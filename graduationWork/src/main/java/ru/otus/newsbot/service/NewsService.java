package ru.otus.newsbot.service;

import ru.otus.newsbot.domain.News;
import ru.otus.newsbot.domain.Status;

import java.util.List;

public interface NewsService {
    List<Status> sendNews(News news);
}
