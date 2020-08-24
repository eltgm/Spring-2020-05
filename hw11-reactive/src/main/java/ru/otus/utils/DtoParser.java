package ru.otus.utils;

import org.springframework.stereotype.Component;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;

@Component
public class DtoParser {

    public Book bookDtoToPojo(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .name(bookDto.getName())
                .publishDate(bookDto.getPublishDate())
                .author(Author.builder()
                        .id(bookDto.getAuthorId())
                        .name(bookDto.getAuthor())
                        .build())
                .genre(Genre.builder()
                        .id(bookDto.getGenreId())
                        .name(bookDto.getGenre())
                        .build())
                .build();
    }
}
