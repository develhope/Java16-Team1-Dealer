package com.develhope.spring.response.clientControllerResponse;

import com.develhope.spring.entities.vehicle.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowVehicleIDResponse {

    private String message;
    private VehicleEntity vehicleEntity;
}
