package ru.otus.newsbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.newsbot.conf.integration.NewsGateway;
import ru.otus.newsbot.domain.Status;
import ru.otus.newsbot.domain.dto.NewsDto;
import ru.otus.newsbot.util.DtoParser;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsControllerImpl implements NewsController {
    private final DtoParser dtoParser;
    private final NewsGateway newsGateway;

    @Override
    @PostMapping("/api/news")
    public List<Status> createNews(NewsDto newsDto) {
        final var statuses = newsGateway.sendNews(dtoParser.newsDtoToPojo(newsDto));

        System.out.println(statuses);
        return statuses;
    }
}
