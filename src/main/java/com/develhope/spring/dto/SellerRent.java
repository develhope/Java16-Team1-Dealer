package com.develhope.spring.dto;

import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.user.SellerEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SellerRent {

    @Autowired
    private IdLogin idLogin;

    private Long sellerId = idLogin.getId();

    private Long clientId;

    private Long vehicleId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startingDate = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endingDate;

    private BigDecimal dailyFee;

    private BigDecimal totalFee;

    private Boolean isPaid;


}
