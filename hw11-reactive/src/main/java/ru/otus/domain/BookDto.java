package ru.otus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("publishDate")
    private String publishDate;
    @JsonProperty("author")
    private String author;
    @JsonProperty("genre")
    private String genre;
    @JsonProperty("authorId")
    private String authorId;
    @JsonProperty("genreId")
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
