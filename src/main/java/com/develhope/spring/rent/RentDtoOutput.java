package com.develhope.spring.rent;

import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentDtoOutput {

    private Long id;
    private SellerEntity sellerId;
    private ClientEntity clientId;
    private VehicleEntity vehicleId;
    private LocalDateTime startingDate;
    private LocalDateTime endingDate;
    private BigDecimal dailyFee;
    private BigDecimal totalFee;
    private Boolean isPaid;
    private RentStatus rentStatus;
}
