package com.develhope.spring.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowSellerRevenueOverTimePeriod {
    private String message;
    private BigDecimal revenue;
}
