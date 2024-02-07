package com.develhope.spring.dto;

import com.develhope.spring.entities.rent.RentEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentDto {


    private Long idSeller;
    private Long idClient;
    private Long idVehicle;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime startRent = LocalDateTime.now();
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime endRent;
    private BigDecimal dailyFee;
    private BigDecimal totalFee;
    private Boolean isPaid;
}