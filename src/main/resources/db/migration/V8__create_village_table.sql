DROP TABLE IF EXISTS villages ;

CREATE TABLE villages (
                      id BIGSERIAL PRIMARY KEY,
                      catfood INT NOT NULL DEFAULT 0,
                      catfood_lastupdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      milk INT NOT NULL DEFAULT 0,
                      milk_lastupdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      catnip INT NOT NULL DEFAULT 0,
                      catnip_lastupdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      brush INT NOT NULL DEFAULT 0,
                      brush_lastupdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      user_id BIGINT NOT NULL,
                      is_active BOOLEAN DEFAULT TRUE,
                      FOREIGN KEY (user_id) REFERENCES users(id)

);