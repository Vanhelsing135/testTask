package com.example.demo.controller;

import com.example.demo.dto.HotelCreateDTO;
import com.example.demo.dto.HotelDTO;
import com.example.demo.dto.HotelDetailsDTO;
import com.example.demo.entity.Hotel;
import com.example.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view/hotels")
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/")
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<HotelDTO> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDetailsDTO> getHotelByID(@PathVariable int id) {
        HotelDetailsDTO hotelDetailsDTO = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotelDetailsDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelDTO>> searchByCriteria(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "amenities", required = false) List<String> amenities
    ) {
        List<HotelDTO> hotelDTO = hotelService.getBySearchingCriteria(name, brand, city, country, amenities);
        return ResponseEntity.ok(hotelDTO);
    }

    @PostMapping("/")
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelCreateDTO hotelCreateDTO) {
        HotelDTO hotelDTO = hotelService.createHotel(hotelCreateDTO);
        return ResponseEntity.ok(hotelDTO);
    }

    @PostMapping("{id}/amenities")
    public ResponseEntity<HotelDetailsDTO> addAmenities(@PathVariable int id, @RequestBody List<String> amenities) {
        HotelDetailsDTO updHotel = hotelService.addAmenities(id, amenities);
        return ResponseEntity.ok(updHotel);
    }

    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> createHistogramByParameter(@PathVariable String param) {
        Map<String, Long> histogram = hotelService.createHistogramByParam(param);
        return ResponseEntity.ok(histogram);
    }
}
