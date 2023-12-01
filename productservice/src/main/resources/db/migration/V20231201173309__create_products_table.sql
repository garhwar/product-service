CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    size VARCHAR(100),
    color VARCHAR(50),
    material VARCHAR(100),
    price NUMERIC(10,2) NOT NULL,
    category_id BIGINT,
    quantity_available INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
