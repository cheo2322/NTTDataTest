CREATE DATABASE nttdata_test;

CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(6) NOT NULL,
    age INT NOT NULL,
    identification VARCHAR(10) UNIQUE NOT NULL,
    address VARCHAR(200) NOT NULL,
    phone_number VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS client (
    id SERIAL PRIMARY KEY,
    client_id VARCHAR NOT NULL,
    password VARCHAR(100) NOT NULL,
    status BOOLEAN NOT NULL,
    person_id BIGINT UNIQUE NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    account_number VARCHAR(10) UNIQUE NOT NULL,
    account_type VARCHAR(8) NOT NULL,
    initial_balance DECIMAL(10, 2) NOT NULL,
    status BOOLEAN NOT NULL,
    client_id VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS movement (
    id SERIAL PRIMARY KEY,
    movement_date TIMESTAMP NOT NULL,
    account_type VARCHAR(8) NOT NULL,
    movement_value DECIMAL(10, 2) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    account_number VARCHAR(10) NOT NULL,
    FOREIGN KEY (account_number) REFERENCES account(account_number)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

