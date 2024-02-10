package com.develhope.spring.admin.adminControllerResponse;

import com.develhope.spring.vehicle.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowListVehicleAdminResponse {

    private String message;
    private List<VehicleEntity> vehicleEntities;
}
