package com.develhope.spring.rent;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentDtoInput {

    private Long id;
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
