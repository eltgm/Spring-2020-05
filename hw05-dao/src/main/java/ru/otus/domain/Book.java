package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Book {
    private long id;
    private String name;
    private String publishDate;
    private Author author;
    private Genre genre;
}
