create table Hotel_amenities(
hotel_id INT,
amenity_id INT,
PRIMARY KEY (hotel_id, amenity_id),
FOREIGN KEY (hotel_id) REFERENCES Hotel(id) ON DELETE CASCADE,
FOREIGN KEY (amenity_id) REFERENCES Amenity(id) ON DELETE CASCADE
)