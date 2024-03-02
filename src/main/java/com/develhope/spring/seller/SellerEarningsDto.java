package com.develhope.spring.seller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerEarningsDto {
    private BigDecimal earnings;
}
