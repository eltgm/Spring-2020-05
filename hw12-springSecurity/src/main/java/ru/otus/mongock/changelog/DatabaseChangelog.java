package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.*;

import java.util.Collections;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {
    private Genre social;
    private Genre satire;
    private Genre fantasy;
    private Author orwell;
    private Author sapkowski;

    @ChangeSet(order = "000", id = "dropDB", author = "vs", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "addGenres", author = "vs", runAlways = true)
    public void insertGenres(MongoTemplate template) {
        social = template.save(new Genre("1", "Social science fiction"));
        satire = template.save(new Genre("2", "Satire"));
        fantasy = template.save(new Genre("3", "Fantasy"));
        template.save(new Genre("4", "Aventure"));
    }

    @ChangeSet(order = "002", id = "addAuthors", author = "vs", runAlways = true)
    public void insertAuthors(MongoTemplate template) {
        orwell = template.save(new Author("1", "George Orwell"));
        sapkowski = template.save(new Author("2", "Andrew Sapkowski"));
    }

    @ChangeSet(order = "004", id = "addComments", author = "vs", runAlways = true)
    public void insertComments(MongoTemplate template) {
        var doc1 = new Comment("1", "1", "Vladislav", "test");
        var doc2 = new Comment("2", "2", "Andrew", "test2");
        var doc3 = new Comment("3", "2", "George", "test3");
        var doc4 = new Comment("4", "3", "Arthur", "test4");

        template.insertAll(List.of(doc1, doc2, doc3, doc4));
    }

    @ChangeSet(order = "003", id = "addBooks", author = "vs", runAlways = true)
    public void insertBooks(MongoTemplate template) {
        var doc1 = new Book("1", "Animal Farm", "17.08.1945", orwell, satire);
        var doc2 = new Book("2", "Nineteen Eighty-Four", "8.06.1949", orwell, social);
        var doc3 = new Book("3", "Ostatnie życzenie", "1996", sapkowski, fantasy);

        template.insertAll(List.of(doc1, doc2, doc3));
    }

    @ChangeSet(order = "004", id = "addUsers", author = "vs", runAlways = true)
    public void insertUsers(MongoTemplate template) {
        var doc1 = new User("user", "$2a$10$GNaoIfuzXeqyFVCvLXIhguYKgFd9.mNNF6HfiLOLD8VSuQMmlWJi6",
                Collections.singletonList(Authority.builder()
                        .authority("ROLE_USER")
                        .build()));
        var doc2 = new User("admin", "$2a$10$XGHnZMtnOskzvcflTipzd.4daS4TmCfht6WVEhb75V34COCTFjIwu",
                Collections.singletonList(Authority.builder()
                        .authority("ROLE_ADMIN")
                        .build()));

        template.insertAll(List.of(doc1, doc2));
    }
}
