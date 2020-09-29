package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.config.mongoAcl.domain.DomainObjectPermission;
import ru.otus.config.mongoAcl.domain.MongoAcl;
import ru.otus.config.mongoAcl.domain.MongoSid;
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
        final var passwordEncoder = new BCryptPasswordEncoder();
        var doc1 = new User("1", "user", passwordEncoder.encode("12344321"),
                Collections.singletonList(Authority.builder()
                        .authority("ROLE_USER")
                        .build()));
        var doc2 = new User("2", "admin", passwordEncoder.encode("43211234"),
                Collections.singletonList(Authority.builder()
                        .authority("ROLE_ADMIN")
                        .build()));
        var doc3 = new User("3", "user_editor", passwordEncoder.encode("11112222"),
                Collections.singletonList(Authority.builder()
                        .authority("ROLE_EDITOR")
                        .build()));

        template.insertAll(List.of(doc1, doc2, doc3));
    }

    @ChangeSet(order = "005", id = "addAcl", author = "vs", runAlways = true)
    public void insertAcl(MongoTemplate mongoTemplate) {
        final var editorSid = new MongoSid("ROLE_EDITOR", false);
        final var adminSid = new MongoSid("ROLE_ADMIN", false);
        final var userSid = new MongoSid("user", true);

        final var firstBookPermissionAdminRead = new DomainObjectPermission("1", adminSid, BasePermission.READ.getMask(), true, true, true);
        final var firstBookPermissionAdminWrite = new DomainObjectPermission("2", adminSid, BasePermission.WRITE.getMask(), true, true, true);
        final var firstBookPermissionEditorRead = new DomainObjectPermission("3", editorSid, BasePermission.READ.getMask(), true, true, true);
        final var firstBookPermissionEditorWrite = new DomainObjectPermission("4", editorSid, BasePermission.WRITE.getMask(), true, true, true);
        final var firstBookPermissionUserRead = new DomainObjectPermission("5", userSid, BasePermission.READ.getMask(), true, true, true);

        /*final var secondBookPermissionAdminRead = new DomainObjectPermission("6", adminSid, BasePermission.READ.getMask(), true, true, true);
        final var secondBookPermissionAdminWrite = new DomainObjectPermission("7", adminSid, BasePermission.WRITE.getMask(), true, true, true);
        final var secondBookPermissionEditorRead = new DomainObjectPermission("8", editorSid, BasePermission.READ.getMask(), true, true, true);
        final var secondBookPermissionEditorWrite = new DomainObjectPermission("9", editorSid, BasePermission.WRITE.getMask(), true, true, true);
        final var secondBookPermissionUserRead = new DomainObjectPermission("10", userSid, BasePermission.READ.getMask(), true, true, true);

        final var thirdBookPermissionAdminRead = new DomainObjectPermission("11", adminSid, BasePermission.READ.getMask(), true, true, true);
        final var thirdBookPermissionAdminWrite = new DomainObjectPermission("12", adminSid, BasePermission.WRITE.getMask(), true, true, true);
        final var thirdBookPermissionEditorRead = new DomainObjectPermission("13", editorSid, BasePermission.READ.getMask(), true, true, true);
        final var thirdBookPermissionEditorWrite = new DomainObjectPermission("14", editorSid, BasePermission.WRITE.getMask(), true, true, true);
        final var thirdBookPermissionUserRead = new DomainObjectPermission("15", userSid, BasePermission.READ.getMask(), true, true, true);*/

        final var firstBookAcl = new MongoAcl("1", Book.class.getName(), "1", adminSid, null, false);
        final var secondBookAcl = new MongoAcl("2", Book.class.getName(), "2", adminSid, null, false);
        final var thirdBookAcl = new MongoAcl("3", Book.class.getName(), "3", adminSid, null, false);

        final var bookPermissions = List.of(firstBookPermissionAdminRead, firstBookPermissionAdminWrite,
                firstBookPermissionEditorRead, firstBookPermissionEditorWrite, firstBookPermissionUserRead);

        firstBookAcl.setPermissions(bookPermissions);
        secondBookAcl.setPermissions(bookPermissions);
        thirdBookAcl.setPermissions(bookPermissions);

        mongoTemplate.insertAll(List.of(firstBookAcl, secondBookAcl, thirdBookAcl));
    }
}
