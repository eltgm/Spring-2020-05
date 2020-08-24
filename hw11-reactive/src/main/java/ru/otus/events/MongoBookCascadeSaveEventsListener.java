package ru.otus.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.Book;
import ru.otus.repository.AuthorsRepository;
import ru.otus.repository.GenresRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeSaveEventsListener extends AbstractMongoEventListener<Book> {
    private final GenresRepository genresRepository;
    private final AuthorsRepository authorsRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        var book = event.getSource();

        final var author = book.getAuthor();
        final var genre = book.getGenre();
        if (author != null) {
            if (author.getId() == null) {
                author.setId(UUID.randomUUID().toString());
            }

            authorsRepository.save(book.getAuthor()).subscribe();
        }
        if (genre != null) {
            if (genre.getId() == null) {
                genre.setId(UUID.randomUUID().toString());
            }

            genresRepository.save(book.getGenre()).subscribe();
        }
    }
}
