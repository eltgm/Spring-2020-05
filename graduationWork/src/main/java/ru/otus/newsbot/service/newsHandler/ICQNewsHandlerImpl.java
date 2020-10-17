package ru.otus.newsbot.service.newsHandler;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mail.im.botapi.BotApiClientController;
import ru.mail.im.botapi.api.entity.SendFileRequest;
import ru.mail.im.botapi.api.entity.SendTextRequest;
import ru.otus.newsbot.domain.Channel;
import ru.otus.newsbot.domain.News;
import ru.otus.newsbot.domain.Photo;
import ru.otus.newsbot.domain.Status;
import ru.otus.newsbot.repository.ChannelsRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.newsbot.domain.Messengers.ICQ;

@Service
@RequiredArgsConstructor
public class ICQNewsHandlerImpl implements NewsHandler {
    private final BotApiClientController icqBot;
    private final ChannelsRepository channelsRepository;

    @Override
    public String getName() {
        return ICQ;
    }

    @Override
    @HystrixCommand(commandKey = ICQ, fallbackMethod = "buildFallbackNewsIcq")
    public List<Status> sendNews(News news) {
        final var statusList = new ArrayList<Status>();
        final var icqChannelsList = channelsRepository
                .findAll()
                .stream()
                .filter(channel -> channel.getMessenger().equals("icq"))
                .collect(Collectors.toList());

        for (Channel channel : icqChannelsList) {
            if (!news.getPhotos().isEmpty()) {
                final var photos = news.getPhotos();

                for (Photo photo : photos) {
                    File tmpFile = null;
                    try {
                        tmpFile = File.createTempFile(photo.getTitle(), "_icq.tmp");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (final var outputStream = new FileOutputStream(tmpFile)) {
                        outputStream.write(photo.getImage().getData());

                        final var sendFileRequest = new SendFileRequest();
                        sendFileRequest.setFile(tmpFile);
                        sendFileRequest.setChatId(channel.getChatId());

                        icqBot.sendFile(sendFileRequest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            final var sendTextRequest = new SendTextRequest();
            sendTextRequest.setChatId(channel.getChatId());
            sendTextRequest.setText(news.getText());
            try {
                final var messageResponse = icqBot.sendTextMessage(sendTextRequest);
                if (messageResponse.isOk()) {
                    statusList.add(Status.builder()
                            .message("ok")
                            .chatId(channel.getChatId())
                            .from(ICQ)
                            .build());
                } else {
                    statusList.add(Status.builder()
                            .message(messageResponse.getDescription())
                            .chatId(channel.getChatId())
                            .from(ICQ)
                            .build());
                }
            } catch (Exception ex) {
                statusList.add(Status.builder()
                        .message(ex.getLocalizedMessage())
                        .chatId(channel.getChatId())
                        .from(ICQ)
                        .build());
            }
        }

        return statusList;
    }

    public List<Status> buildFallbackNewsIcq() {
        return Collections.singletonList(Status.builder()
                .chatId(ICQ)
                .message("timeout")
                .build());
    }
}
