package ru.otus.newsbot.conf.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.newsbot.domain.News;
import ru.otus.newsbot.domain.Status;

import java.util.List;

@MessagingGateway
public interface NewsGateway {
    @Gateway(requestChannel = "newsControllerChannel")
    List<Status> sendNews(News news);
}
