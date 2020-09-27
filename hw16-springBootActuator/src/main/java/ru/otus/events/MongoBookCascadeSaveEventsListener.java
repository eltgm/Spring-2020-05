package ru.otus.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.Book;
import ru.otus.repository.AuthorsRepository;
import ru.otus.repository.GenresRepository;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeSaveEventsListener extends AbstractMongoEventListener<Book> {
    private final GenresRepository genresRepository;
    private final AuthorsRepository authorsRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        var book = event.getSource();
        if (book.getAuthor() != null) {
            authorsRepository.save(book.getAuthor());
        }
        if (book.getGenre() != null) {
            genresRepository.save(book.getGenre());
        }
    }
}
