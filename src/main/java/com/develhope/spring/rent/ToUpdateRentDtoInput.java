package com.develhope.spring.rent;


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
public class ToUpdateRentDtoInput {

    private LocalDateTime rentStartingDate;
    private LocalDateTime rentEndingDate;
    private BigDecimal dailyFee;
    private BigDecimal totalFee;
    private Boolean isPaid;

}
