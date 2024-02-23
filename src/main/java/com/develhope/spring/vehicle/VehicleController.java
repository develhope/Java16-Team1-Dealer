package com.develhope.spring.vehicle;

import com.develhope.spring.vehicle.VehicleEntity;
import com.develhope.spring.vehicle.VehicleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/vehicles")
@Tag(name = "Vehicle Controller", description = "Vehicle Controller API")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;






}
