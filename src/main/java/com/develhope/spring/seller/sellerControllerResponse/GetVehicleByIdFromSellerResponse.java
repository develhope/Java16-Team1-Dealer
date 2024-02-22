package com.develhope.spring.seller.sellerControllerResponse;

import com.develhope.spring.vehicle.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetVehicleByIdFromSellerResponse {

    private String message;
    private VehicleEntity vehicleEntity;
}
