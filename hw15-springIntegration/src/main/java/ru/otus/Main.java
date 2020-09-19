package ru.otus;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.config.NewsGateway;
import ru.otus.domain.News;

import java.util.Date;

@SpringBootApplication
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        final var context = SpringApplication.run(Main.class, args);

        final var newsGateway = context.getBean(NewsGateway.class);
        while (true) {
            Thread.sleep(1000);

            System.out.println("Получили новость в контроллере...");
            newsGateway.sendNews(News.builder()
                    .date(new Date())
                    .text(String.valueOf(System.currentTimeMillis()))
                    .build());
        }
    }
}
