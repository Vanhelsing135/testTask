package com.example.demo.dto;

import com.example.demo.entity.Amenity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDetailsDTO {
    private int id;
    private String name;
    private String description;
    private String brand;
    private AddressDTO address;
    private ContactDTO contact;
    private ArrivalTimeDTO arrivalTime;
    private List<String> amenities;
}
