package com.develhope.spring.vehicle;

import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.rent.RentEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleSalesInfoDto {
    private String brand;
    private String model;
    private String colour;
    private String accessories;
    private Integer engineCapacity;
    private String fuelType;
    private GearType gearType;
    private Integer hp;
    private BigDecimal price;
    private Integer totalSales;
}
