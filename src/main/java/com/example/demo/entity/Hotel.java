package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Hotel")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //    @ElementCollection
    //    private ArrayList<String> amenities;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    private String brand;
    private String description;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "houseNumber", column = @Column(name = "houseNumber")),
            @AttributeOverride(name = "street", column = @Column(name = "street")),
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "country", column = @Column(name = "country")),
            @AttributeOverride(name = "postCode", column = @Column(name = "postCode"))
    })
    private Address address;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phone", column = @Column(name = "phone")),
            @AttributeOverride(name = "email", column = @Column(name = "email"))
    })
    private Contact contact;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "checkIn", column = @Column(name = "checkIn")),
            @AttributeOverride(name = "checkOut", column = @Column(name = "checkOut"))
    })
    private ArrivalTime arrivalTime;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Hotel_amenities",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private List<Amenity> amenities;
}

