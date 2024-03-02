package com.develhope.spring.seller;

import com.develhope.spring.vehicle.VehicleSalesInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SellerRepository extends JpaRepository<SellerEntity, Long> {

    @Query(name = "showRevenueOverTimePeriod",
            nativeQuery = true)
    BigDecimal showRevenueOverTimePeriod(@Param(value = "id") Long id,
                                         @Param(value = "firstdate") String firstDate,
                                         @Param(value = "seconddate") String secondDate);


    @Query(name = "showVehiclesSoldOverTimePeriod",
            nativeQuery = true)
    Integer showVehiclesSoldOverTimePeriod(@Param(value = "id") Long id,
                                           @Param(value = "firstdate") String firstDate,
                                           @Param(value = "seconddate") String secondDate);


}
