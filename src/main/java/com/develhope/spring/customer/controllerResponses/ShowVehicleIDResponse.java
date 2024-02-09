package com.develhope.spring.customer.controllerResponses;

import com.develhope.spring.shared.vehicle.VehicleEntity;
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
