package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorsRepositoryImpl implements AuthorsRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Author author) {
        em.persist(author);
    }

    @Override
    public List<Author> getAll() {
        var query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }
}
