package ru.otus.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @OneToMany(targetEntity = Book.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<Book> books;
}
