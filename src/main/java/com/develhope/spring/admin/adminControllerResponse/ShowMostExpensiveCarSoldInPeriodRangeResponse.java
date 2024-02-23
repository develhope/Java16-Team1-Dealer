package com.develhope.spring.admin.adminControllerResponse;

import com.develhope.spring.vehicle.VehicleSalesInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowMostExpensiveCarSoldInPeriodRangeResponse {
    private String message;
    private VehicleSalesInfoDto vehicleDTO;
}
