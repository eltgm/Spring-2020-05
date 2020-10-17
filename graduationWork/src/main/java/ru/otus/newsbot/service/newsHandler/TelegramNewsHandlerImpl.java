package ru.otus.newsbot.service.newsHandler;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InputMedia;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;
import com.pengrad.telegrambot.request.SendMediaGroup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.newsbot.domain.Channel;
import ru.otus.newsbot.domain.News;
import ru.otus.newsbot.domain.Status;
import ru.otus.newsbot.repository.ChannelsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.otus.newsbot.domain.Messengers.TELEGRAM;


@Service
@RequiredArgsConstructor
public class TelegramNewsHandlerImpl implements NewsHandler {
    private final TelegramBot telegramBot;
    private final ChannelsRepository channelsRepository;

    @Override
    public String getName() {
        return TELEGRAM;
    }

    @Override
    @HystrixCommand(commandKey = TELEGRAM, fallbackMethod = "buildFallbackNewsTelegram")
    public List<Status> sendNews(News news) {
        final var statusList = new ArrayList<Status>();
        final var channelList = channelsRepository
                .findAll()
                .stream()
                .filter(channel -> channel.getMessenger().equals("telegram"))
                .collect(Collectors.toList());

        BaseResponse messageResponse;
        for (Channel channel : channelList) {
            SendMediaGroup newsPost = null;

            if (!news.getPhotos().isEmpty()) {
                final var photos = news.getPhotos();
                final var inputMedia = new InputMedia[photos.size()];

                final var firstPhoto = new InputMediaPhoto(photos.get(0).getImage().getData());
                firstPhoto.caption(news.getText());
                inputMedia[0] = firstPhoto;
                for (int i = 1; i < photos.size(); i++) {
                    inputMedia[i] = new InputMediaPhoto(photos.get(i).getImage().getData());
                }

                newsPost = new SendMediaGroup(channel.getChatId(), inputMedia);

            }

            try {
                messageResponse = telegramBot.execute(Objects.requireNonNullElseGet(newsPost, () -> new SendMessage(channel.getChatId(), news.getText())));

                if (messageResponse.isOk()) {
                    statusList.add(Status.builder()
                            .message("ok")
                            .from(TELEGRAM)
                            .chatId(channel.getChatId())
                            .build());

                } else {
                    statusList.add(Status.builder()
                            .message(messageResponse.description())
                            .from(TELEGRAM)
                            .chatId(channel.getChatId())
                            .build());
                }
            } catch (Exception ex) {
                statusList.add(Status.builder()
                        .message(ex.getLocalizedMessage())
                        .chatId(channel.getChatId())
                        .from(TELEGRAM)
                        .build());
            }
        }

        return statusList;
    }

    public List<Status> buildFallbackNewsTelegram() {
        return Collections.singletonList(Status.builder()
                .from(TELEGRAM)
                .message("timeout")
                .build());
    }
}
