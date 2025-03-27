CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    account_number VARCHAR(50) PRIMARY KEY,
    account_type VARCHAR(50) NOT NULL,
    initial_balance DECIMAL(10, 2) NOT NULL,
    status BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS movement (
    id SERIAL PRIMARY KEY,
    movement_date TIMESTAMP NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    account_number VARCHAR(50) NOT NULL,
    FOREIGN KEY (account_number) REFERENCES account(account_number)
);

