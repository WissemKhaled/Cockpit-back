CREATE TABLE IF NOT EXISTS subcontractor (
    id SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    email VARCHAR(45) NOT NULL,
    subcontractorStatus VARCHAR(20) NOT NULL
);
