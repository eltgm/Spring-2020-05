package ru.otus.service.newsHandler;

import org.springframework.stereotype.Service;
import ru.otus.domain.News;
import ru.otus.domain.Status;

import static ru.otus.domain.Messengers.TELEGRAM;

@Service
public class TelegramNewsHandlerImpl implements NewsHandler {
    @Override
    public String getName() {
        return TELEGRAM;
    }

    @Override
    public Status sendNews(News news) {
        System.out.println("Отправили в Telegram - " + news.getText());

        return Status.builder()
                .message("ok")
                .from(TELEGRAM)
                .build();
    }
}
