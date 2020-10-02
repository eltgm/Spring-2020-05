package ru.otus.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.domain.News;
import ru.otus.domain.Status;

import java.util.List;

@MessagingGateway
public interface NewsGateway {
    @Gateway(requestChannel = "newsControllerChannel")
    List<Status> sendNews(News news);
}
