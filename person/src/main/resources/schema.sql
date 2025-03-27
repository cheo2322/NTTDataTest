CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(6),
    age INT,
    identification VARCHAR(10),
    address VARCHAR(200),
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS client (
    id SERIAL PRIMARY KEY,
    clientId VARCHAR NOT NULL,
    password VARCHAR(100) NOT NULL,
    status BOOLEAN NOT NULL,
    person_id INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(id)
);
