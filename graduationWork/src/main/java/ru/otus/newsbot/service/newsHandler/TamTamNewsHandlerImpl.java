package ru.otus.newsbot.service.newsHandler;

import chat.tamtam.botapi.TamTamBotAPI;
import chat.tamtam.botapi.TamTamUploadAPI;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.*;
import chat.tamtam.botapi.queries.SendMessageQuery;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

import static ru.otus.newsbot.domain.Messengers.TAMTAM;


@Service
@RequiredArgsConstructor
public class TamTamNewsHandlerImpl implements NewsHandler {
    private final TamTamBotAPI tamTamBot;
    private final TamTamClient tamTamClient;
    private final ChannelsRepository channelsRepository;

    @Override
    public String getName() {
        return TAMTAM;
    }

    @Override
    @HystrixCommand(commandKey = TAMTAM, fallbackMethod = "buildFallbackNewsTamtam")
    public List<Status> sendNews(News news) {
        final var statusList = new ArrayList<Status>();
        final var channelList = channelsRepository
                .findAll()
                .stream()
                .filter(channel -> channel.getMessenger().equals("tamtam"))
                .collect(Collectors.toList());

        for (Channel channel : channelList) {
            SendMessageResult sendMessageResult;
            try {
                sendMessageResult = sendMessageToTamTam(channel, news);

                if (sendMessageResult.getMessage() != null) {
                    statusList.add(Status.builder()
                            .message("ok")
                            .chatId(channel.getChatId())
                            .from(TAMTAM)
                            .build());
                } else {
                    statusList.add(Status.builder()
                            .message("fail")
                            .chatId(channel.getChatId())
                            .from(TAMTAM)
                            .build());
                }
            } catch (Exception ex) {
                statusList.add(Status.builder()
                        .message(ex.getLocalizedMessage())
                        .chatId(channel.getChatId())
                        .from(TAMTAM)
                        .build());
            }
        }

        return statusList;
    }

    private SendMessageResult sendMessageToTamTam(Channel channel, News news) throws ClientException, IOException, APIException {
        final var photos = news.getPhotos();
        final var photoBody = new ArrayList<AttachmentRequest>();

        for (Photo photo : photos) {
            final var photoAttachmentRequestPayload = new PhotoAttachmentRequestPayload();

            UploadEndpoint endpoint = tamTamBot.getUploadUrl(UploadType.IMAGE).execute();
            String uploadUrl = endpoint.getUrl();
            TamTamUploadAPI uploadAPI = new TamTamUploadAPI(tamTamClient);

            final var tmpFile = File.createTempFile(photo.getTitle(), "tamtam_.tmp");
            try (final var outputStream = new FileOutputStream(tmpFile)) {
                outputStream.write(photo.getImage().getData());

                var photoTokens = uploadAPI.uploadImage(uploadUrl, tmpFile).execute();

                photoTokens.getPhotos().forEach(photoAttachmentRequestPayload::putPhotosItem);

                photoBody.add(new PhotoAttachmentRequest(photoAttachmentRequestPayload));
            }

        }
        NewMessageBody body = new NewMessageBody(news.getText(), photoBody
                , null);
        SendMessageQuery sendMessageQuery = tamTamBot.sendMessage(body).chatId(Long.valueOf(channel.getChatId()));

        return sendMessageQuery.execute();
    }

    public List<Status> buildFallbackNewsTamtam() {
        return Collections.singletonList(Status.builder()
                .from(TAMTAM)
                .message("timeout")
                .build());
    }
}
