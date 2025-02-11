package com.example.demo.repository;

import com.example.demo.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Query("select hotel from Hotel hotel where " +
            "(:name is NULL or hotel.name like :name) and " +
            "(:brand is NULL or hotel.brand LIKE :brand) and " +
            "(:city is NULL or hotel.address.city like :city) and " +
            "(:country is NULL or hotel.address.country like :country) and " +
            "(:amenities is NULL or EXISTS (select am from hotel.amenities am where am.name in :amenities))")
    List<Hotel> findBySearchCriteria(@Param("name") String name, @Param("brand") String brand, @Param("city") String city, @Param("country") String country, @Param("amenities") List<String> amenities);

    @Query("select h.address.city, COUNT(h) from Hotel h group by h.address.city")
    List<Object[]> groupByCity();

    @Query("select h.brand, COUNT(h) from Hotel h group by h.brand")
    List<Object[]> groupByBrand();

    @Query("select h.address.country, COUNT(h) from Hotel h group by h.address.country")
    List<Object[]> groupByCountry();

    @Query("select amenity.name, COUNT(h) from Hotel h join h.amenities amenity group by amenity.name")
    List<Object[]> groupByAmenities();

    Optional<Hotel> findByName(String name);
}
