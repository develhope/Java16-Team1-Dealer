package com.develhope.spring.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleSalesInfoDto {
    private VehicleEntity vehicle;
    private Long totalSales;
}
