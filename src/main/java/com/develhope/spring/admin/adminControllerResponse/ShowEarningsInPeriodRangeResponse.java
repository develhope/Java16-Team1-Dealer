package com.develhope.spring.admin.adminControllerResponse;

import com.develhope.spring.vehicle.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowEarningsInPeriodRangeResponse {

    private String message;
    private BigDecimal earnings;
}
