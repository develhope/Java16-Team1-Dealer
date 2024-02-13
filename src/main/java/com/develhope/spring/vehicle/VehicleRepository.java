package com.develhope.spring.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    @Query(value = "SELECT * FROM vehicle AS v WHERE v.sell_type = 'RFD'", nativeQuery = true)
    List<VehicleEntity> showAllVehiclesRfd();

    @Query(value = "SELECT * FROM vehicle AS v WHERE v.rentable = TRUE AND v.sell_type IN ('RFD', 'USED')", nativeQuery = true)
    List<VehicleEntity> showAllRentableVehicles();

    @Query(value = "SELECT * FROM vehicle AS v WHERE v.price >= :min AND v.price <= :max", nativeQuery = true)
    List<VehicleEntity> showAllVehiclesByRangePrice(@Param("min") BigDecimal minPrice, @Param("max") BigDecimal maxPrice);

    @Query(value = "SELECT * FROM vehicle AS v WHERE v.brand LIKE %:brand% OR v.colour LIKE %:color% OR v.model LIKE %:model%", nativeQuery = true)
    List<VehicleEntity> showAllVehiclesFiltered(@Param("color") String color, @Param("brand") String brand, @Param("model") String model);

    @Transactional
    @Modifying
    @Query(value = "UPDATE vehicle AS v SET v.rentable = false WHERE v.id = :vehicleid", nativeQuery = true)
    void updateVehicleRentability(@Param("vehicleid") Long vehicleId);

    @Transactional
    @Modifying
    @Query(value = "SELECT v.*, COUNT(o.id_vehicle) AS total_sales " +
            "FROM vehicle AS v " +
            "LEFT JOIN orders AS o ON o.id_vehicle = v.id " +
            "WHERE :firstdate <= o.date_purch AND o.date_purch <= :seconddate " +
            "AND o.order_stat != 'CANCELED' " +
            "GROUP BY v.id;", nativeQuery = true)
    List<VehicleSalesInfoDto> showMostSoldCarInPeriodRange(@Param(value = "firstdate") String firstDate,
                                                           @Param(value = "seconddate") String secondDate);


    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM vehicle AS v " +
            "LEFT JOIN orders AS o ON o.id_vehicle = v.id " +
            "WHERE :firstdate <= o.date_purch AND o.date_purch <= :seconddate " +
            "AND o.order_stat != 'CANCELED' " +
            "GROUP BY v.id;" , nativeQuery = true)
    List<VehicleEntity> showMostExpensiveCarInPeriodRange(@Param(value = "firstdate") String firstDate,
                                                         @Param(value = "seconddate") String secondDate);

    @Transactional
    @Modifying
    @Query(value = "SELECT v.*, COUNT(o.id_vehicle) AS total_sales " +
            "FROM vehicle AS v " +
            "LEFT JOIN orders AS o ON o.id_vehicle = v.id " +
            "AND o.order_stat != 'CANCELED' " +
            "GROUP BY v.id;", nativeQuery = true)
    List<VehicleSalesInfoDto> showMostSoldCarEver();
}



