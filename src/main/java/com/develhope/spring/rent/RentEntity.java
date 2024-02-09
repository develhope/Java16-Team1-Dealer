package com.develhope.spring.rent;

import com.develhope.spring.customer.CustomerEntity;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.shared.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rents")
@Getter
@Setter
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
    private CustomerEntity clientId;

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

    @Enumerated(EnumType.STRING)
    private RentStatus rentStatus;
}
