package ru.otus;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableCircuitBreaker
@SpringBootApplication
@EnableMongoRepositories
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
