package com.develhope.spring.entities.rent;

import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.user.SellerEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rents")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_seller")
    private SellerEntity sellerId;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private ClientEntity clientId;

    @ManyToOne
    @JoinColumn(name = "id_vehicle")
    private VehicleEntity vehicleId;

    @Column(name = "starting_date")
    private LocalDateTime startingDate;

    @Column(name = "ending_date")
    private LocalDateTime endingDate;

    @Column(name = "daily_fee")
    private BigDecimal dailyFee;

    @Column(name = "total_fee")
    private BigDecimal totalFee;

    @Column(name = "is_paid")
    private Boolean isPaid;

}
