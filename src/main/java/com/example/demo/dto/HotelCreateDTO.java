package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelCreateDTO {
    private String name;
    private String description;
    private String brand;
    private AddressDTO address;
    private ContactDTO contact;
    private ArrivalTimeDTO arrivalTime;
}
