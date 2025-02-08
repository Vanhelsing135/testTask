package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Address;
import com.example.demo.entity.Amenity;
import com.example.demo.entity.Hotel;
import com.example.demo.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public HotelDTO convertToDTO(Hotel hotel) {
        StringBuilder fullAddress = new StringBuilder();
        Address address = hotel.getAddress();
        fullAddress.append(address.getHouseNumber()).append(" ").
                append(address.getStreet()).append(", ").
                append(address.getCity()).append(", ").
                append(address.getPostCode()).append(", ").
                append(address.getCountry());
        return new HotelDTO(hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                fullAddress.toString(),
                hotel.getContact().getPhone());

    }

    public HotelDetailsDTO getHotelById(int id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel with id " + id + " not found"));
        return convertToDetailsDTO(hotel);
    }

    public HotelDetailsDTO convertToDetailsDTO(Hotel hotel) {
        Address address = hotel.getAddress();
        AddressDTO addressDTO = new AddressDTO(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostCode()
        );

        ContactDTO contactDTO = new ContactDTO(
                hotel.getContact().getPhone(),
                hotel.getContact().getEmail()
        );

        ArrivalTimeDTO arrivalTimeDTO = new ArrivalTimeDTO(
                hotel.getArrivalTime().getCheckIn(),
                hotel.getArrivalTime().getCheckOut()
        );

        List<String> amenities = hotel.getAmenities().stream()
                .map(Amenity::getName).toList();

        return new HotelDetailsDTO(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getBrand(),
                addressDTO,
                contactDTO,
                arrivalTimeDTO,
                amenities
        );
    }
}
