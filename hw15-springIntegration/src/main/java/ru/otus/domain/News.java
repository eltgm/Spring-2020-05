package ru.otus.domain;

import lombok.*;

import java.io.File;
import java.util.Date;
import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private String id;
    private String text;
    private List<File> photos;
    private Date date;
    private String messenger;
}
