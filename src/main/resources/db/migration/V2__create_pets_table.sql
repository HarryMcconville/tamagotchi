DROP TABLE IF EXISTS pets;

CREATE TABLE pets (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255),
                      user_id BIGINT NOT NULL,
                      FOREIGN KEY (user_id) REFERENCES users(id)
);