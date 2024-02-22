package com.develhope.spring.vehicle;

import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.rent.RentEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    private Long id;
    private String brand;
    private String model;
    private Integer engineCapacity;
    private String colour;
    private Integer hp;
    private GearType gearType;
    private LocalDate registerYear;
    private String fuelType;
    private BigDecimal price;
    private Integer priceDscnt;
    private String accessories;
    private Boolean rentable;
    private SellType sellType;


}
