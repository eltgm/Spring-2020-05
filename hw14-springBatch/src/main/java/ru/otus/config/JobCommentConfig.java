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
import ru.otus.domain.mongo.Comment;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobCommentConfig {
    private final MongoTemplate mongoTemplate;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public MongoItemReader<Comment> mongoCommentItemReader() {
        return new MongoItemReaderBuilder<Comment>()
                .name("mongoItemReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(ru.otus.domain.mongo.Comment.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<Comment, ru.otus.domain.sql.Comment> commentProcessor() {
        return comment -> ru.otus.domain.sql.Comment.builder()
                .id(Long.valueOf(comment.getId()))
                .bookId(Long.valueOf(comment.getBookId()))
                .userName(comment.getUserName())
                .text(comment.getText())
                .build();
    }

    @Bean
    public JpaItemWriter<ru.otus.domain.sql.Comment> jpaCommentItemWriter() {
        return new JpaItemWriterBuilder<ru.otus.domain.sql.Comment>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step migrateComments() {
        return stepBuilderFactory.get("migrateBooks")
                .<ru.otus.domain.mongo.Comment, ru.otus.domain.sql.Comment>chunk(10)
                .reader(mongoCommentItemReader())
                .processor(commentProcessor())
                .writer(jpaCommentItemWriter())
                .build();
    }
}
