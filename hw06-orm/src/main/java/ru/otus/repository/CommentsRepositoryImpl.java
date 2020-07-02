package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentsRepositoryImpl implements CommentsRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> getAll() {
        final var query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public void create(Comment comment) {
        em.persist(comment);
    }

    @Override
    public void deleteById(long id) {
        final var query = em.createQuery("delete from Comment c " +
                "where c.id = :id");
        query.setParameter("id", id);

        query.executeUpdate();
    }
}
