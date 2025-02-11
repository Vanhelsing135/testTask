create table Hotel_amenities(
    hotel_id INT,
    amenity VARCHAR(255),
    FOREIGN KEY (hotel_id) REFERENCES Hotel(id)
);