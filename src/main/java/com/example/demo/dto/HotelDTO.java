package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private int id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
