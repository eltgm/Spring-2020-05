package ru.otus.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.domain.News;

@MessagingGateway
public interface NewsGateway {
    @Gateway(requestChannel = "newsControllerChannel")
    void sendNews(News news);
}
