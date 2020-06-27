package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BooksDaoJdbc implements BooksDao {
    private final NamedParameterJdbcOperations namedJdbc;

    @Override
    public void create(Book book) {
        var paramMap = Map.of(
                "id", book.getId(),
                "name", book.getName(),
                "publish_date", book.getPublishDate(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()
        );

        namedJdbc.update("insert into book (id, `name`, `publish_date`, author_id, genre_id) " +
                        "values (:id, :name, :publish_date, :author_id, :genre_id)",
                paramMap);
    }

    @Override
    public void update(Book book) {
        var paramMap = Map.of(
                "id", book.getId(),
                "name", book.getName(),
                "publish_date", book.getPublishDate(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()
        );

        namedJdbc.update("update book set `name` = :name, `publish_date` = :publish_date, `author_id` = :author_id, `genre_id` = :genre_id  " +
                        "where id =:id",
                paramMap);
    }

    @Override
    public Optional<Book> getById(long id) {
        var params = Collections.singletonMap("id", id);

        try {
            final var book = namedJdbc.queryForObject(
                    "select * from book b " +
                            "inner join `genre` g on g.id = b.genre_id " +
                            "inner join `author` a on a.id = b.author_id " +
                            "where b.id = :id", params, new BookMapper()
            );

            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        return namedJdbc.query("select * from book b " +
                "inner join `genre` g on g.id = b.genre_id " +
                "inner join `author` a on a.id = b.author_id", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        var params = Collections.singletonMap("id", id);
        namedJdbc.update(
                "delete from book where id = :id", params
        );
    }

    @Override
    public int getCount() {
        return namedJdbc.queryForObject("select count(*) from book", Collections.EMPTY_MAP, Integer.class);
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            var id = resultSet.getLong("id");
            var name = resultSet.getString("name");
            var publishDate = resultSet.getString("publish_date");

            var authorName = resultSet.getString(9);
            var authorId = resultSet.getLong("author_id");
            var author = new Author(authorId, authorName, null);

            var genreName = resultSet.getString(7);
            var genreId = resultSet.getLong("genre_id");
            var genre = new Genre(genreId, genreName, null);
            return new Book(id, name, publishDate, author, genre);
        }
    }
}
