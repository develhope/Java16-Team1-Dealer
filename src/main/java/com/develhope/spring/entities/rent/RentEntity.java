package com.develhope.spring.entities.rent;

import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.user.SellerEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "rents")
public class RentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_seller")
    private SellerEntity sellerEntity;

    @OneToOne
    @JoinColumn(name = "id_client")
    private ClientEntity clientEntity;

    @OneToOne
    @JoinColumn(name = "id_vehicle")
    private VehicleEntity vehicleEntity;

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
