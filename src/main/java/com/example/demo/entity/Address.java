package com.example.demo.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    private int houseNumber;
    private String street;
    private String city;
    private String country;
    private int postCode;
}
