package ru.otus.newsbot.conf;

import chat.tamtam.botapi.TamTamBotAPI;
import chat.tamtam.botapi.client.TamTamClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TamTamBotConfig {

    @Bean
    public TamTamBotAPI getTamTamBot() {
        return TamTamBotAPI.create("-d0L5StsCHxo0bqfWOx5iMVn3J9eGaZ50ab2I5g-oxc");
    }

    @Bean
    public TamTamClient getTamTamClient() {
        return TamTamClient.create("-d0L5StsCHxo0bqfWOx5iMVn3J9eGaZ50ab2I5g-oxc");
    }
}
