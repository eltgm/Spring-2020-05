package ru.otus.newsbot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.Binary;

@Data
@Builder
@AllArgsConstructor
public class Photo {
    private String title;
    private Binary image;
}
