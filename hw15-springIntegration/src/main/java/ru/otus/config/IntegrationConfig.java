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
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import ru.otus.domain.News;

import java.util.List;

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
                .transform((GenericTransformer<News, List<Message<News>>>) source -> {
                    final var messageToTamTam = MessageBuilder.withPayload(source)
                            .setHeader("to", "tamtam")
                            .build();
                    final var messageToTelegram = MessageBuilder.withPayload(source)
                            .setHeader("to", "telegram")
                            .build();
                    return List.of(messageToTamTam, messageToTelegram);
                })
                .split()
                .handle()
                .aggregate()
                .get();
    }
}
