CREATE TYPE user_role AS ENUM ('admin', 'manager', 'user');

CREATE TABLE IF NOT EXISTS users (
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(50)   NOT NULL UNIQUE,
    password      VARCHAR(255)  NOT NULL,
    email         VARCHAR(100),
    full_name     VARCHAR(100),
    role          user_role     NOT NULL DEFAULT 'user',
    is_active     BOOLEAN       NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS products (
    id          BIGSERIAL PRIMARY KEY,
     name        VARCHAR(100) NOT NULL,
    description TEXT,
    price       DECIMAL(10,2) DEFAULT 0.00,
    quantity    INT           DEFAULT 0 CHECK (quantity >= 0),
    is_active   BOOLEAN       NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS inventory_operations (
    id                  BIGSERIAL PRIMARY KEY,
    product_id          BIGINT NOT NULL,
    user_id             BIGINT NOT NULL,
    operation_timestamp TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    quantity            INT NOT NULL,
    comment             TEXT,
    CONSTRAINT fk_product
    FOREIGN KEY (product_id)
    REFERENCES products (id)
    ON DELETE RESTRICT,
    CONSTRAINT fk_user
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE RESTRICT
);
