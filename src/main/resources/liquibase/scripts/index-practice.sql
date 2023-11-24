-- liquibase formatted sql
--changeset Dima:1
CREATE SCHEMA if not exist graduate-work;
--changeset Dima:2
create table if not exist ads
(
     id BIGINT primary key,
     title not null,
     description not null,
     price not null,
     author_id not null,
     ad not null,
     image not null
);
create table if not exist comments
(
     id BIGINT primary key,
     text not null,
     createdAt not null,
     author_id not null,
     ad_id not null
);
create table if not exist users
(
     id BIGINT primary key,
     username not null,
     firstName not null,
     lastName not null,
     password not null,
     phone not null,
     role not null,
     avatar not null
);