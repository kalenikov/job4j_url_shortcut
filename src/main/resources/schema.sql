DROP TABLE IF EXISTS links;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles
(
    id   serial primary key,
    name VARCHAR(50) NOT NULL unique
);

CREATE TABLE users
(
    id       serial primary key,
    site     varchar(255),
    username VARCHAR(50)  NOT NULL unique,
    password VARCHAR(100) NOT NULL,
    role_id  int references roles
);

CREATE TABLE links
(
    id       serial primary key,
    url      varchar(2000)               not null,
    code     varchar(10)                 not null,
    created  timestamp without time zone not null default now(),
    count    int                         not null default 0,
    owner_id int references users
);