package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {

}
