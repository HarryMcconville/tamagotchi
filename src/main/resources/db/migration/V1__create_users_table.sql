DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id bigserial PRIMARY KEY,
                       display_name varchar(50) NOT NULL UNIQUE,
                       enabled boolean NOT NULL
);