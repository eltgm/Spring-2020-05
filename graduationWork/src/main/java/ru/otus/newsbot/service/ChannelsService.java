package ru.otus.newsbot.service;

import ru.otus.newsbot.domain.Channel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ChannelsService {
    CompletableFuture<List<Channel>> getAllChannels();

    void addChannel(Channel channel);

    void deleteChannel(String id);
}
