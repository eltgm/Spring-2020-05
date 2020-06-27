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
public class GenresDaoJdbc implements GenresDao {
    private final NamedParameterJdbcOperations namedJdbc;

    @Override
    public void create(Genre genre) {
        var paramMap = Map.of(
                "id", genre.getId(),
                "name", genre.getName()
        );

        namedJdbc.update("insert into genre (id, `name`) " +
                        "values (:id, :name)",
                paramMap);
    }

    @Override
    public List<Genre> getAll() {
        return namedJdbc.getJdbcOperations().query("select * from genre g " +
                "left join `book` b on g.id = b.genre_id " +
                "left join `author` a on b.author_id = a.id ", new GenresMapper());
    }

    private class GenresMapper implements
            ResultSetExtractor<List<Genre>> {
        @Override
        public List<Genre> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            var genres = new HashMap<Long, Genre>();

            while (resultSet.next()) {
                var id = resultSet.getLong("id");

                var genre = genres.get(id);
                if (genre == null) {
                    genre = new Genre(id, resultSet.getString("name"), new ArrayList<>());

                    genres.put(id, genre);
                }
                final var author = new Author(resultSet.getLong(8), resultSet.getString(9), null);
                final var book = new Book(
                        resultSet.getLong(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        author,
                        null);
                genre.getBooks().add(book);
            }
            return new ArrayList<>(Objects.requireNonNull(genres).values());
        }
    }
}
