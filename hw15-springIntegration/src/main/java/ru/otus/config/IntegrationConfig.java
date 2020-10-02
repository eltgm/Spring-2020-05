package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.transformer.GenericTransformer;
import ru.otus.domain.News;
import ru.otus.domain.Status;

import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.domain.Messengers.TAMTAM;
import static ru.otus.domain.Messengers.TELEGRAM;

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

                    return List.of(newsToTamTam, newsToTelegram);
                })
                .split()
                .handle("newsServiceImpl", "sendNews")
                .aggregate()
                .transform((GenericTransformer<List<Status>, List<Status>>) source -> {
                    final var failed = source.stream()
                            .filter(status -> status.getMessage().equals("failed"))
                            .collect(Collectors.toList());
                    if (failed.size() > 0) {
                        return failed;
                    }

                    return List.of(Status.builder()
                            .message("ok")
                            .from("all")
                            .build());
                })
                .get();
    }
}
