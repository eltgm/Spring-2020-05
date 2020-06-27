DROP TABLE IF EXISTS BOOK CASCADE;
DROP TABLE IF EXISTS AUTHOR CASCADE;
DROP TABLE IF EXISTS GENRE CASCADE;

CREATE TABLE AUTHOR(
ID BIGINT PRIMARY KEY ,
NAME VARCHAR(255));

CREATE TABLE GENRE(
ID BIGINT PRIMARY KEY ,
NAME VARCHAR(255));

CREATE TABLE BOOK(
ID BIGINT PRIMARY KEY,
NAME VARCHAR(255),
PUBLISH_DATE VARCHAR(255),
AUTHOR_ID BIGINT REFERENCES AUTHOR(ID),
GENRE_ID BIGINT REFERENCES GENRE(ID));