package ru.otus.newsbot.conf;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mail.im.botapi.BotApiClient;
import ru.mail.im.botapi.BotApiClientController;

@Configuration
public class ICQBotConfig {

    @Bean
    @SneakyThrows
    public BotApiClientController getICQBot() {
        final var client = new BotApiClient("001.2522537143.0609586087:754890223");

        return BotApiClientController.startBot(client);
    }
}
