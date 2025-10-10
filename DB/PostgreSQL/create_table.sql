-- Таблиця поїздів
CREATE TABLE trains (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Таблиця локомотивів
CREATE TABLE locomotives (
    id SERIAL PRIMARY KEY,
    train_id INTEGER UNIQUE REFERENCES trains(id) ON DELETE CASCADE,
    weight DOUBLE PRECISION NOT NULL,
    suspension_width DOUBLE PRECISION NOT NULL,
    potency DOUBLE PRECISION NOT NULL,
    fuel_type VARCHAR(20) NOT NULL,
    max_speed DOUBLE PRECISION NOT NULL
);

-- Таблиця пасажирських вагонів
CREATE TABLE passenger_cars (
    id SERIAL PRIMARY KEY,
    train_id INTEGER REFERENCES trains(id) ON DELETE CASCADE,
    weight DOUBLE PRECISION NOT NULL,
    suspension_width DOUBLE PRECISION NOT NULL,
    number_cities INTEGER NOT NULL,
    level VARCHAR(20),
    current_passengers INTEGER NOT NULL
);

-- Таблиця вантажних вагонів
CREATE TABLE freight_cars (
    id SERIAL PRIMARY KEY,
    train_id INTEGER REFERENCES trains(id) ON DELETE CASCADE,
    weight DOUBLE PRECISION NOT NULL,
    suspension_width DOUBLE PRECISION NOT NULL,
    max_volume DOUBLE PRECISION NOT NULL,
    max_weight DOUBLE PRECISION NOT NULL,
    current_volume DOUBLE PRECISION NOT NULL,
    current_weight DOUBLE PRECISION NOT NULL
);
