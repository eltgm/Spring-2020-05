package ru.otus.newsbot.service.newsHandler;


import ru.otus.newsbot.domain.News;
import ru.otus.newsbot.domain.Status;

import java.util.List;

public interface NewsHandler {
    String getName();

    List<Status> sendNews(News news);
}
