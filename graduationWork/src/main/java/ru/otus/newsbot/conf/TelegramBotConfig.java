package ru.otus.newsbot.conf;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {

    @Bean
    public TelegramBot getTelegramBot() {
        return new TelegramBot("1259941571:AAEU_vR4zk4QMKgq5VqgPRoVxhqnAmtQXNU");
    }
}
