package ru.otus.newsbot.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    private String id;
    private String text;
    private List<Photo> photos;
    private Date date;
    @With
    private String messenger;
}
