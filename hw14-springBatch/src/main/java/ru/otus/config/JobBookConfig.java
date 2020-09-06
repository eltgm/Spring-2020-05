package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.mongo.Book;
import ru.otus.domain.sql.Author;
import ru.otus.domain.sql.Genre;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobBookConfig {
    private final MongoTemplate mongoTemplate;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public MongoItemReader<Book> mongoBookItemReader() {
        return new MongoItemReaderBuilder<Book>()
                .name("mongoItemReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(ru.otus.domain.mongo.Book.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<Book, ru.otus.domain.sql.Book> bookProcessor() {
        return book -> ru.otus.domain.sql.Book.builder()
                .id(Long.valueOf(book.getId()))
                .name(book.getName())
                .publishDate(book.getPublishDate())
                .author(Author.builder()
                        .id(Long.valueOf(book.getAuthor().getId()))
                        .name(book.getAuthor().getName())
                        .build())
                .genre(Genre.builder()
                        .id(Long.valueOf(book.getGenre().getId()))
                        .name(book.getGenre().getName())
                        .build())
                .build();
    }

    @Bean
    public JpaItemWriter<ru.otus.domain.sql.Book> jpaBookItemWriter() {
        return new JpaItemWriterBuilder<ru.otus.domain.sql.Book>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step migrateBooks() {
        return stepBuilderFactory.get("migrateBooks")
                .<ru.otus.domain.mongo.Book, ru.otus.domain.sql.Book>chunk(10)
                .reader(mongoBookItemReader())
                .processor(bookProcessor())
                .writer(jpaBookItemWriter())
                .build();
    }
}
