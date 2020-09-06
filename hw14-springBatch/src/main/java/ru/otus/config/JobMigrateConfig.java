package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class JobMigrateConfig {
    private final MongoTemplate mongoTemplate;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBookConfig jobBookConfig;
    private final JobCommentConfig jobCommentConfig;

    @Bean
    public Step clearMongoCollections() {
        return stepBuilderFactory.get("clearAuthor")
                .tasklet((stepContribution, chunkContext) -> {
                    mongoTemplate.dropCollection(ru.otus.domain.mongo.Author.class);
                    mongoTemplate.dropCollection(ru.otus.domain.mongo.Genre.class);
                    mongoTemplate.dropCollection(ru.otus.domain.mongo.Book.class);
                    mongoTemplate.dropCollection(ru.otus.domain.mongo.Comment.class);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Job migrateJob() {
        return jobBuilderFactory.get("migrateJob")
                .start(jobBookConfig.migrateBooks())
                .next(jobCommentConfig.migrateComments())
                .next(clearMongoCollections())
                .build();
    }
}
