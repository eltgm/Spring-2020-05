package ru.otus.newsbot.conf.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.transformer.GenericTransformer;
import ru.otus.newsbot.domain.News;

import java.util.List;

import static ru.otus.newsbot.domain.Messengers.*;


@Configuration
@EnableIntegration
@IntegrationComponentScan
public class IntegrationConfig {
    @Bean
    public PublishSubscribeChannel newsControllerChannel() {
        return MessageChannels.publishSubscribe()
                .get();
    }

    @Bean
    public IntegrationFlow newsFlow() {
        return IntegrationFlows.from("newsControllerChannel")
                .transform((GenericTransformer<News, List<News>>) source -> {
                    final var newsToTamTam = source.withMessenger(TAMTAM);
                    final var newsToTelegram = source.withMessenger(TELEGRAM);
                    final var newsToIcq = source.withMessenger(ICQ);

                    return List.of(newsToTamTam, newsToTelegram, newsToIcq);
                })
                .split()
                .handle("newsServiceImpl", "sendNews")
                .aggregate()
                .get();
    }
}
