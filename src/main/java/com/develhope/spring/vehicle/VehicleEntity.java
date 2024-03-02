package com.develhope.spring.vehicle;

import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.rent.RentEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "showMostSoldCarInPeriodRange",
                query = "SELECT v.brand, v.model, v.colour, v.accessories, v.engine_capacity, v.fuel_type, v.gear_type, v.hp, v.price, COUNT(v.model) AS total_sales " +
                        "FROM vehicle AS v " +
                        "INNER JOIN orders AS o ON o.id_vehicle = v.id " +
                        "WHERE :firstdate <= o.date_purch AND o.date_purch <= :seconddate " +
                        "AND o.order_stat != 'CANCELED' " +
                        "GROUP BY v.brand, v.model, v.colour, v.accessories, v.engine_capacity, v.fuel_type, v.gear_type, v.hp, v.price " +
                        "ORDER BY total_sales DESC " +
                        "LIMIT 1;",
                resultSetMapping = "vehicle_sales_info_dto"
        ),
        @NamedNativeQuery(
                name = "showMostExpensiveCarInPeriodRange",
                query = "SELECT v.brand, v.model, v.colour, v.accessories, v.engine_capacity, v.fuel_type, v.gear_type, v.hp, v.price, COUNT(v.model) AS total_sales " +
                        "FROM vehicle AS v " +
                        "INNER JOIN orders AS o ON o.id_vehicle = v.id " +
                        "WHERE :firstdate <= o.date_purch AND o.date_purch <= :seconddate " +
                        "AND o.order_stat != 'CANCELED' " +
                        "GROUP BY v.brand, v.model, v.colour, v.accessories, v.engine_capacity, v.fuel_type, v.gear_type, v.hp, v.price " +
                        "ORDER BY v.price DESC " +
                        "LIMIT 1;",
                resultSetMapping = "vehicle_sales_info_dto"
        ),
        @NamedNativeQuery(
                name = "showMostSoldCarEver",
                query = "SELECT v.brand, v.model, v.colour, v.accessories, v.engine_capacity, v.fuel_type, v.gear_type, v.hp, v.price, COUNT(v.model) AS total_sales " +
                        "FROM vehicle AS v " +
                        "INNER JOIN orders AS o ON o.id_vehicle = v.id " +
                        "AND o.order_stat != 'CANCELED' " +
                        "GROUP BY v.brand, v.model, v.colour, v.accessories, v.engine_capacity, v.fuel_type, v.gear_type, v.hp, v.price " +
                        "ORDER BY total_sales DESC " +
                        "LIMIT 1;",
                resultSetMapping = "vehicle_sales_info_dto"
        )
})
@SqlResultSetMapping(
        name = "vehicle_sales_info_dto",
        classes = @ConstructorResult(
                targetClass = VehicleSalesInfoDto.class,
                columns = {
                        @ColumnResult(name = "brand", type = String.class),
                        @ColumnResult(name = "model", type = String.class),
                        @ColumnResult(name = "colour", type = String.class),
                        @ColumnResult(name = "accessories", type = String.class),
                        @ColumnResult(name = "engine_capacity", type = Integer.class),
                        @ColumnResult(name = "fuel_type", type = String.class),
                        @ColumnResult(name = "gear_type", type = GearType.class),
                        @ColumnResult(name = "hp", type = Integer.class),
                        @ColumnResult(name = "price", type = BigDecimal.class),
                        @ColumnResult(name = "total_sales", type = Integer.class),
                }
        )
)
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
