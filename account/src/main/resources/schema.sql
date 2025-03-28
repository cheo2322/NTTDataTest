DROP TABLE IF EXISTS movement;
DROP TABLE IF EXISTS account;

CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    initial_balance DECIMAL(10, 2) NOT NULL,
    status BOOLEAN NOT NULL,
    client_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS movement (
    id SERIAL PRIMARY KEY,
    movement_date TIMESTAMP NOT NULL,
    account_type VARCHAR(8) NOT NULL,
    movement_value DECIMAL(10, 2) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    account_id BIGINT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

