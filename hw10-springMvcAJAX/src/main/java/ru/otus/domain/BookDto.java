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
    private String authorId;
    private String genreId;

    public Book toPojo() {
        return Book.builder()
                .id(this.id)
                .name(this.name)
                .publishDate(this.publishDate)
                .author(Author.builder()
                        .id(authorId)
                        .name(this.author)
                        .build())
                .genre(Genre.builder()
                        .id(genreId)
                        .name(this.genre)
                        .build())
                .build();
    }
}
