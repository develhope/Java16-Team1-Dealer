package com.develhope.spring.seller;

import com.develhope.spring.vehicle.VehicleSalesInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SellerRepository extends JpaRepository<SellerEntity, Long> {

    @Query(name = "SELECT SUM(v.price - v.price_dscnt) " +
            "FROM orders AS o " +
            "JOIN vehicle AS v ON o.id_vehicle = v.id " +
            "WHERE o.id_seller = :id " +
            "AND o.order_stat != 'CANCELED' " +
            "AND o.date_purch >= :firstDate " +
            "AND o.date_purch <= :secondDate ;",
            nativeQuery = true)
    BigDecimal showSellerRevenueOverTimePeriod(@Param(value = "id") Long id,
                                               @Param(value = "firstDate") String firstDate,
                                               @Param(value = "secondDate") String secondDate);


    @Query(name = "SELECT COUNT(*) FROM orders AS o " +
            "WHERE o.id_seller = :id " +
            "AND o.date_purch <= :firstDate " +
            "AND o.date_purch >= :secondDate " +
            "AND o.order_stat != 'CANCELED'",
            nativeQuery = true)
    Integer showSellerVehiclesSoldOverTimePeriod(@Param(value = "id") Long id,
                                                 @Param(value = "firstDate") String firstDate,
                                                 @Param(value = "secondDate") String secondDate);


}
