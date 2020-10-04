package ru.otus.service;


import ru.otus.domain.News;
import ru.otus.domain.Status;

public interface NewsService {
    Status sendNews(News news);
}
