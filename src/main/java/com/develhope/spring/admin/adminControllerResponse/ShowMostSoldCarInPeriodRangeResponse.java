package com.develhope.spring.admin.adminControllerResponse;

import com.develhope.spring.vehicle.VehicleSalesInfoDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowMostSoldCarInPeriodRangeResponse {

    private String message;
    private VehicleSalesInfoDto vehicleSalesInfoDto;

}
