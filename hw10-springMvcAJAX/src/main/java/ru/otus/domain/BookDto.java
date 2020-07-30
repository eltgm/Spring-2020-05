package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private String id;
    private String name;
    private String publishDate;
    private String author;
    private String genre;

    public Book toPojo() {
        return Book.builder()
                .id(this.id)
                .name(this.name)
                .publishDate(this.publishDate)
                .author(Author.builder()
                        .name(this.author)
                        .build())
                .genre(Genre.builder()
                        .name(this.genre)
                        .build())
                .build();
    }
}
