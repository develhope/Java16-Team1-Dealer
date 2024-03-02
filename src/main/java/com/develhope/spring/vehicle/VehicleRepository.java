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

    @Query(name = "showMostSoldCarInPeriodRange", nativeQuery = true)
    VehicleSalesInfoDto showMostSoldCarInPeriodRange(@Param(value = "firstdate") String firstDate,
                                                     @Param(value = "seconddate") String secondDate);
    @Query(name = "showMostExpensiveCarInPeriodRange", nativeQuery = true)
    VehicleSalesInfoDto showMostExpensiveCarInPeriodRange(@Param(value = "firstdate") String firstDate,
                                                          @Param(value = "seconddate") String secondDate);
    @Query(name = "showMostSoldCarEver", nativeQuery = true)
    VehicleSalesInfoDto showMostSoldCarEver();

    @Query(value = "SELECT SUM(v.price - v.price_dscnt) AS earnings " +
            "FROM vehicle AS v " +
            "INNER JOIN orders AS o " +
            "ON v.id = o.id_vehicle " +
            "WHERE o.order_stat != 'CANCELED' " +
            "AND o.date_purch >= :firstDate " +
            "AND o.date_purch <= :secondDate ;", nativeQuery = true)
    BigDecimal showEarningsInPeriodRange(@Param("firstDate") String firstDate,@Param("secondDate") String secondDate);


    @Query(value = "SELECT * FROM vehicle WHERE sell_type = :selltype ;\n", nativeQuery = true)
    List<VehicleEntity> showFilteredVehicles(@Param("selltype") String sellType);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vehicle AS v " +
            "JOIN rents AS r " +
            "ON v.id = r.id_vehicle " +
            "SET v.rentable = 1 " +
            "WHERE r.id = :parameter ;", nativeQuery = true)
    void resetVehicleRentability(@Param("parameter") Long rentId);
}



