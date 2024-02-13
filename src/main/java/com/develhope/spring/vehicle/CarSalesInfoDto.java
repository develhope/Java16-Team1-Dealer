package com.develhope.spring.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarSalesInfoDto {
    private VehicleEntity vehicle;
    private Long totalSales;
}
