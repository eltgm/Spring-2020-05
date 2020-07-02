insert into author(id, `name`)
values (1, 'George Orwell');
insert into author(id, `name`)
values (2, 'Andrew Sapkowski');

insert into genre(id, `name`)
values (1, 'Social science fiction');
insert into genre(id, `name`)
values (2, 'Satire');
insert into genre(id, `name`)
values (3, 'Fantasy');
insert into genre(id, `name`)
values (4, 'Aventure');

insert into book (id, `name`, `publish_date`, author_id, genre_id)
values (1, 'Animal Farm', '17.08.1945', 1, 2);
insert into book (id, `name`, `publish_date`, author_id, genre_id)
values (2, 'Nineteen Eighty-Four', '8.06.1949', 1, 1);
insert into book (id, `name`, `publish_date`, author_id, genre_id)
values (3, 'Ostatnie życzenie', '1996', 2, 3);

insert into comment(id, book_id, `user_name`, `text`)
values (1, 1, 'Vladislav', 'test');
insert into comment(id, book_id, `user_name`, `text`)
values (2, 2, 'Andrew', 'test2');
insert into comment(id, book_id, `user_name`, `text`)
values (3, 2, 'George', 'test3');
insert into comment(id, book_id, `user_name`, `text`)
values (4, 3, 'Arthur', 'test4');
