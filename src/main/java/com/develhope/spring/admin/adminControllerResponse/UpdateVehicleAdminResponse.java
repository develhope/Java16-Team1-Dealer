package com.develhope.spring.admin.adminControllerResponse;

import com.develhope.spring.vehicle.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVehicleAdminResponse {

    private String message;
    private VehicleEntity vehicle;
}
