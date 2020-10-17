package ru.otus.newsbot.util;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.newsbot.domain.News;
import ru.otus.newsbot.domain.Photo;
import ru.otus.newsbot.domain.dto.NewsDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class DtoParser {
    public News newsDtoToPojo(NewsDto newsDto) {
        final var photos = new ArrayList<Photo>();

        if (newsDto.getPhotos() != null) {
            for (MultipartFile photo : newsDto.getPhotos()) {
                try {
                    final var bytes = photo.getBytes();
                    if (bytes.length == 0) {
                        continue;
                    }

                    photos.add(Photo.builder()
                            .title(photo.getName())
                            .image(new Binary(BsonBinarySubType.BINARY, photo.getBytes()))
                            .build());
                } catch (IOException e) {
                    return News.builder()
                            .text(newsDto.getText())
                            .date(new Date())
                            .build();
                }
            }
        }

        return News.builder()
                .text(newsDto.getText())
                .date(new Date())
                .photos(photos)
                .build();
    }
}
