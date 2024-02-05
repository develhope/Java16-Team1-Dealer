package com.develhope.spring.entities.vehicle;

import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.rent.RentEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    @OneToMany(mappedBy = "vehicleId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderEntity> orderList;

    @OneToMany(mappedBy = "vehicleId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RentEntity> rentList;

}
