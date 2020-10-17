package ru.otus.newsbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.newsbot.domain.Channel;
import ru.otus.newsbot.service.ChannelsService;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
public class ChannelsControllerImpl implements ChannelsController {
    private final ChannelsService channelsService;

    @Override
    @GetMapping("/api/channel")
    public Callable<List<Channel>> getAllChannels() {
        return () -> channelsService.getAllChannels().get();
    }

    @Override
    @PostMapping("/api/channel")
    public void addNewChannel(@RequestBody Channel channel) {
        channelsService.addChannel(channel);
    }

    @Override
    @DeleteMapping("/api/channel")
    public void deleteChannel(@RequestParam(value = "id") String id) {
        channelsService.deleteChannel(id);
    }
}
