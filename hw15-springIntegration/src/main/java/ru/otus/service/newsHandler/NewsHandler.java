package ru.otus.service.newsHandler;

import ru.otus.domain.News;
import ru.otus.domain.Status;

public interface NewsHandler {
    String getName();

    Status sendNews(News news);
}
