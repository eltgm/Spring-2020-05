package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "publish_date", nullable = false, unique = true)
    private String publishDate;
    @ManyToOne(targetEntity = Author.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    private Author author;
    @ManyToOne(targetEntity = Genre.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "book_id")
    private List<Comment> comments;
}
