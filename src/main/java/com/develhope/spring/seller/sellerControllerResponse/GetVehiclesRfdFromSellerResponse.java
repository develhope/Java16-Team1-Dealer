package com.develhope.spring.seller.sellerControllerResponse;

import com.develhope.spring.vehicle.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetVehiclesRfdFromSellerResponse {

    private String message;
    private List<VehicleEntity> vehicles;
}
