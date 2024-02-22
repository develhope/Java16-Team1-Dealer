package com.develhope.spring.seller.sellerControllerResponse;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ErrorMessageSeller {

    public String showFoundVehicle(Long idVehicle) {
        return "Vehicle with id:" + idVehicle + "correctly found";
    }

    public String vehicleNotFound(Long idVehicle) {
        return "Vehicle with id:" + idVehicle + "has not found";
    }
}
