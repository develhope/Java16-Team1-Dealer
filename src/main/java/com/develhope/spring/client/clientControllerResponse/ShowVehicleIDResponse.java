package com.develhope.spring.client.clientControllerResponse;

import com.develhope.spring.vehicle.VehicleEntity;
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
