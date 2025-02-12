package com.example.demo.controller;

import com.example.demo.dto.HotelCreateDTO;
import com.example.demo.dto.HotelDTO;
import com.example.demo.dto.HotelDetailsDTO;
import com.example.demo.entity.Hotel;
import com.example.demo.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Получить список всех отелей с краткой информацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список отелей успешно получен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelDTO.class)))
    })
    @GetMapping("/")
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<HotelDTO> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @Operation(summary = "Получить полную информацию об отеле по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация об отеле успешно получена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Отель c таким id не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<HotelDetailsDTO> getHotelByID(@PathVariable int id) {
        HotelDetailsDTO hotelDetailsDTO = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotelDetailsDTO);
    }

    @Operation(summary = "Поиск отелей по критериям")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поиск успешно завершен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelDTO.class)))
    })
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

    @Operation(summary = "Создать новый отель")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Отель успешно создан",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelCreateDTO hotelCreateDTO) {
        HotelDTO hotelDTO = hotelService.createHotel(hotelCreateDTO);
        return ResponseEntity.ok(hotelDTO);
    }

    @Operation(summary = "Добавить удобства в отель по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удобства успешно добавлены",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Отель с таким id не найден", content = @Content)
    })
    @PostMapping("{id}/amenities")
    public ResponseEntity<HotelDetailsDTO> addAmenities(@PathVariable int id, @RequestBody List<String> amenities) {
        HotelDetailsDTO updHotel = hotelService.addAmenities(id, amenities);
        return ResponseEntity.ok(updHotel);
    }

    @Operation(summary = "Создать гистограмму по параметру")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Гистограмма успешно создана",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Недопустимый параметр", content = @Content)
    })
    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> createHistogramByParameter(@PathVariable String param) {
        Map<String, Long> histogram = hotelService.createHistogramByParam(param);
        return ResponseEntity.ok(histogram);
    }
}
