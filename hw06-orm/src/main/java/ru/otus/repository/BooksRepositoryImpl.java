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
        var query = em.createQuery("update Book b " +
                "set b.author = (select a from Author a where a.id = :a_id), " +
                "b.genre = (select g from Genre g where g.id = :g_id), " +
                "b.name = :name, " +
                "b.publishDate = :date " +
                "where b.id = :id");

        query.setParameter("a_id", book.getAuthor().getId());
        query.setParameter("g_id", book.getGenre().getId());
        query.setParameter("name", book.getName());
        query.setParameter("date", book.getPublishDate());
        query.setParameter("id", book.getId());

        query.executeUpdate();
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        final var query = em.createQuery("select b from Book b " +
                "join fetch b.genre " +
                "join fetch b.author", Book.class);

        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        var query = em.createQuery("delete from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);

        query.executeUpdate();
    }

    @Override
    public long getCount() {
        var query = em.createQuery("select count(b) from Book b");
        return (long) query.getSingleResult();
    }
}
