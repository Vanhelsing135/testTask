create table Hotel(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    brand VARCHAR(100),
    houseNumber INT,
    street VARCHAR(150),
    city VARCHAR(100),
    country VARCHAR(100),
    postCode INT,
    phone VARCHAR(50),
    email VARCHAR(255),
    checkIn VARCHAR(255),
    checkOut VARCHAR(255)
    );