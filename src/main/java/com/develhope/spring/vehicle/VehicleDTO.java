package com.develhope.spring.vehicle;

import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.rent.RentEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private Integer engineCapacity;
    @Column(nullable = false)
    private String colour;
    @Column(nullable = false)
    private Integer hp;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GearType gearType;
    @Column(nullable = false)
    private LocalDate registerYear;
    @Column(nullable = false)
    private String fuelType;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer priceDscnt;
    @Column(nullable = false)
    private String accessories;
    @Column(nullable = false)
    private Boolean rentable;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SellType sellType;

    @OneToMany(mappedBy = "vehicleId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderEntity> orderList;

    @OneToMany(mappedBy = "vehicleId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RentEntity> rentList;



}
