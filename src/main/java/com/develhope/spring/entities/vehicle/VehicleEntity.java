package com.develhope.spring.entities.vehicle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
@Entity
@Table(name = "Vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private int engineCapacity;
    @Column(nullable = false)
    private String colour;
    @Column(nullable = false)
    private int hp;
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
    private int priceDscnt;
    @Column(nullable = false)
    private String accessories;
    @Column(nullable = false)
    private Boolean rentable;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SellType sellType;

}
