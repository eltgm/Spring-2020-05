package ru.otus.service;


import ru.otus.domain.News;

public interface NewsService {
    String sendNewsToTelegram(News news);

    String sendNewsToTamTam(News news);
}
