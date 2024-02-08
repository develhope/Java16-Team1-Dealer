package com.develhope.spring.response.clientControllerResponse;

import com.develhope.spring.entities.vehicle.VehicleEntity;
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
