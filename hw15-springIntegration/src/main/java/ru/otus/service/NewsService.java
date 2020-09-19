package ru.otus.service;


import ru.otus.domain.News;

public interface NewsService {
    void sendNewsToTelegram(News news);

    void sendNewsToTamTam(News news);
}
