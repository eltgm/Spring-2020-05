package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class AuthorsDaoJdbc implements AuthorsDao {
    private final NamedParameterJdbcOperations namedJdbc;

    @Override
    public void create(Author author) {
        var paramMap = Map.of(
                "id", author.getId(),
                "name", author.getName()
        );

        namedJdbc.update("insert into author (id, `name`) " +
                        "values (:id, :name)",
                paramMap);
    }

    @Override
    public List<Author> getAll() {
        return namedJdbc.getJdbcOperations().query("select a.id, a.name, g.id, g.name, b.id, b.name, b.publish_date, b.author_id, b.genre_id " +
                "from author a " +
                "left join `book` b on a.id = b.author_id " +
                "left join `genre` g on g.id = b.genre_id ", new AuthorsMapper());
    }

    private static class AuthorsMapper implements
            ResultSetExtractor<List<Author>> {

        @Override
        public List<Author> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            var authors = new HashMap<Long, Author>();

            while (resultSet.next()) {
                var id = resultSet.getLong("id");

                var author = authors.get(id);
                if (author == null) {
                    author = new Author(id, resultSet.getString("name"), new ArrayList<>());

                    authors.put(id, author);
                }

                final var genre = new Genre(resultSet.getLong(8), resultSet.getString(9), null);
                final var book = new Book(resultSet.getLong(3), resultSet.getString(4), resultSet.getString(5),
                        null,
                        genre);
                author.getBooks().add(book);
            }
            return new ArrayList<>(Objects.requireNonNull(authors).values());
        }
    }
}
