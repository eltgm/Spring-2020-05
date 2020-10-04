package ru.otus.service.newsHandler;

import org.springframework.stereotype.Service;
import ru.otus.domain.News;
import ru.otus.domain.Status;

import static ru.otus.domain.Messengers.TAMTAM;

@Service
public class TamTamNewsHandlerImpl implements NewsHandler {
    @Override
    public String getName() {
        return TAMTAM;
    }

    @Override
    public Status sendNews(News news) {
        System.out.println("Отправили в TamTam - " + news.getText());

        return Status.builder()
                .message("ok")
                .from(TAMTAM)
                .build();
    }
}
