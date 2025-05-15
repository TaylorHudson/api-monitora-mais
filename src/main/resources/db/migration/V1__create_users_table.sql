CREATE TABLE IF NOT EXISTS users (
    registration VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    role VARCHAR(50),
    user_type VARCHAR(50) NOT NULL
);
