package ru.otus;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
