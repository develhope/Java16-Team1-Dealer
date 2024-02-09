package com.develhope.spring.customer.controllerResponses;

import com.develhope.spring.shared.vehicle.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListVehicleFilterResponse {

    private String message;
    private List<VehicleEntity> vehicleEntityList;

}
