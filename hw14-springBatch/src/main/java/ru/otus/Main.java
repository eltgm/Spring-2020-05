package ru.otus;

import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        final var run = SpringApplication.run(Main.class, args);
        final var migrateJob = run.getBean("migrateJob", Job.class);
        final var jobLauncher = run.getBean("jobLauncher", JobLauncher.class);

        jobLauncher.run(migrateJob, new JobParameters());
    }
}
