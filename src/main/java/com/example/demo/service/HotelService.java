package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.AmenityRepository;
import com.example.demo.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, AmenityRepository amenityRepository) {
        this.hotelRepository = hotelRepository;
        this.amenityRepository = amenityRepository;
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

    public List<HotelDTO> getBySearchingCriteria(String name, String brand, String city, String country, List<String> amenities) {
        List<Hotel> hotels = hotelRepository.findBySearchCriteria(name, brand, city, country, amenities);

        if (hotels.isEmpty()) {
            throw new EntityNotFoundException("No hotels found matching the search criteria.");
        }

        return hotels.stream().map(this::convertToDTO).toList();
    }

    public HotelDTO createHotel(HotelCreateDTO hotelCreateDTO) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelCreateDTO.getName());
        if (hotelCreateDTO.getName() == null) {
            throw new IllegalArgumentException("field 'name' cannot be empty!");
        }
        hotel.setDescription(hotelCreateDTO.getDescription());
        hotel.setBrand(hotelCreateDTO.getBrand());

        Address address = new Address();
        AddressDTO addressDTO = hotelCreateDTO.getAddress();
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        address.setStreet(addressDTO.getStreet());
        address.setPostCode(addressDTO.getPostCode());
        address.setHouseNumber(addressDTO.getHouseNumber());
        hotel.setAddress(address);

        Contact contact = new Contact();
        ContactDTO contactDTO = hotelCreateDTO.getContact();
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
        hotel.setContact(contact);

        ArrivalTime arrivalTime = new ArrivalTime();
        ArrivalTimeDTO arrivalTimeDTO = hotelCreateDTO.getArrivalTime();
        arrivalTime.setCheckIn(arrivalTimeDTO.getCheckIn());
        arrivalTime.setCheckOut(arrivalTimeDTO.getCheckOut());
        hotel.setArrivalTime(arrivalTime);

        hotel = hotelRepository.save(hotel);

        return convertToDTO(hotel);
    }

    public HotelDetailsDTO addAmenities(int hotelId, List<String> amenities) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new EntityNotFoundException("Hotel with id " + hotelId + " not found"));

        if (hotel.getAmenities() == null) {
            hotel.setAmenities(new ArrayList<>());
        }

        for (String amenityName : amenities) {
            Amenity amenity = amenityRepository.findByName(amenityName)
                    .orElseGet(() -> {
                        Amenity newAmenity = new Amenity();
                        newAmenity.setName(amenityName);
                        return amenityRepository.save(newAmenity);
                    });

            if (!hotel.getAmenities().contains(amenity)) {
                hotel.getAmenities().add(amenity);
            }
        }

        hotelRepository.save(hotel);
        return convertToDetailsDTO(hotel);
    }
}
