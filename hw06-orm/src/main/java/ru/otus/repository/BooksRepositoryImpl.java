package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BooksRepositoryImpl implements BooksRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Book book) {
        em.persist(book);
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        final var book = em.find(Book.class, id);
        if (book != null) {
            book.getComments().isEmpty();
        }

        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> getAll() {
        final var query = em.createQuery("select b from Book b", Book.class);

        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        final var book = em.find(Book.class, id);

        em.remove(book);
    }

    @Override
    public long getCount() {
        final var query = em.createQuery("select count(b) from Book b");
        return (long) query.getSingleResult();
    }
}
