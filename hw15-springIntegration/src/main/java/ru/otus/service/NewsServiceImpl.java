package ru.otus.service;

import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.stereotype.Service;
import ru.otus.domain.News;


@Service
public class NewsServiceImpl implements NewsService {

    public NewsServiceImpl(PublishSubscribeChannel newsControllerChannel) {
        newsControllerChannel.subscribe(message -> {
            final var news = (News) message.getPayload();

            sendNewsToTamTam(news);
            sendNewsToTelegram(news);
        });
    }

    public void sendNewsToTelegram(News news) {
        System.out.println("Отправили в Telegram - " + news.getText());
    }

    public void sendNewsToTamTam(News news) {
        System.out.println("Отправили в TamTam - " + news.getText());
    }
}
