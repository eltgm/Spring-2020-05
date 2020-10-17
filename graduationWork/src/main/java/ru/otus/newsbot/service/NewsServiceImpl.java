package ru.otus.newsbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.newsbot.domain.News;
import ru.otus.newsbot.domain.Status;
import ru.otus.newsbot.service.newsHandler.NewsHandler;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final List<NewsHandler> handlers;

    @Override
    public List<Status> sendNews(News news) {
        for (NewsHandler handler : handlers) {
            if (handler.getName().equals(news.getMessenger())) {
                try {
                    return handler.sendNews(news);
                } catch (Exception ex) {
                    return List.of(Status.builder()
                            .message("Не найден handler")
                            .from(news.getMessenger())
                            .build());
                }
            }
        }

        return List.of(Status.builder()
                .message("Не найден handler")
                .from(news.getMessenger())
                .build());
    }
}

