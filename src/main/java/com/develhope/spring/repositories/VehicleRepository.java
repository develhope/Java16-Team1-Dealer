package com.develhope.spring.repositories;

import com.develhope.spring.entities.vehicle.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    @Query(value = "SELECT * FROM vehicle AS v WHERE v.sell_type = 'RFD'", nativeQuery = true)
    List<VehicleEntity> showAllVehiclesRfd();

    @Query(value = "SELECT * FROM vehicle AS v WHERE v.rentable = TRUE AND v.sell_type IN ('RFD', 'USED')", nativeQuery = true)
    List<VehicleEntity> showAllRentableVehicles();


}
