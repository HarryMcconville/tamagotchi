DROP TABLE IF EXISTS pets;

CREATE TABLE village (
                      id BIGSERIAL PRIMARY KEY,
                      catfood BIGINT NOT NULL DEFAULT 0,
                      catfood_lastupdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                      milk BIGINT NOT NULL DEFAULT 0,
                      milk_lastupdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      catnip BIGINT NOT NULL DEFAULT 0,
                      catnip_lastupdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      brush BIGINT NOT NULL DEFAULT 0,
                      brush_lastupdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      user_id BIGINT NOT NULL,
                      is_active BOOLEAN DEFAULT TRUE,
                      FOREIGN KEY (user_id) REFERENCES users(id)

);