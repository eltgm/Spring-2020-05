package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.News;


@Service
public class NewsServiceImpl implements NewsService {

    public String sendNewsToTelegram(News news) {
        System.out.println("Отправили в Telegram - " + news.getText());

        return "OK from telegram!";
    }

    public String sendNewsToTamTam(News news) {
        System.out.println("Отправили в TamTam - " + news.getText());

        return "OK from tamtam!";
    }
}
