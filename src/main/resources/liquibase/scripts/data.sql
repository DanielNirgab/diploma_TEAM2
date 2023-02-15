-- liquibase formatted sql

--changeset iaktov:1
CREATE TABLE Users
(
    id         bigint primary key,
    email      TEXT,
    first_name TEXT,
    password   TEXT,
    last_name  TEXT,
    phone      TEXT,
    reg_date   TEXT,
    username   varchar,
    role       integer,
    city       TEXT

);

CREATE TABLE Ads
(
    id          bigint generated by default as identity primary key,
    price       BIGINT,
    title       TEXT,
    description text,
    user_id     bigint references Users (id) on delete cascade

);

CREATE TABLE Comments
(
    id         integer generated by default as identity primary key,
    created_at TEXT,
    text       TEXT,
    user_id    bigint REFERENCES Users (id) ON DELETE CASCADE,
    ads_id     bigint REFERENCES Ads (id) ON DELETE CASCADE
);


create table Images
(
    id         bigint generated by default as identity primary key,
    file_size  bigint,
    media_type varchar(255),
    image      bytea,
    ads_id     bigint REFERENCES Ads (id) ON DELETE CASCADE
);

--changeset anmalashenko:3
alter table Users
    add column enabled boolean;
create sequence users_id_seq;
alter table Users
    alter column id set default nextval('users_id_seq');
alter sequence users_id_seq OWNED BY Users.id;

create table if not exists authorities
(
    username varchar
(
    255
) not null primary key,
    authority varchar
(
    32
)
    );
