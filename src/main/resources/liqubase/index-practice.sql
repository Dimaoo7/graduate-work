-- liquebase formatted sql
--changeset lBorisov:1
create table ads
(
     id primary key ,
     title not null,
     description not null,
     price not null,
     author_id not null,
     ad not null,
     image not null
);
--changeset lBorisov:2
create table comments
(
     id primary key ,
     text not null,
     createdAt not null,
     author_id not null,
     ad_id not null,
);
--changeset lBorisov:3
create table users
(
     id primary key ,
     username not null,
     firstName not null,
     lastName not null,
     password not null,
     phone not null,
     role not null,
     avatar not null,
);