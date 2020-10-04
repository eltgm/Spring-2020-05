package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.News;
import ru.otus.domain.Status;
import ru.otus.service.newsHandler.NewsHandler;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final List<NewsHandler> handlers;

    @Override
    public Status sendNews(News news) {
        for (NewsHandler handler : handlers) {
            if (handler.getName().equals(news.getMessenger())) {
                return handler.sendNews(news);
            }
        }

        return Status.builder()
                .message("Не найден handler")
                .from(news.getMessenger())
                .build();
    }
}
