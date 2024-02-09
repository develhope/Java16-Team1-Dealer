package com.develhope.spring.shared.vehicle;

import com.develhope.spring.shared.vehicle.VehicleEntity;
import com.develhope.spring.shared.vehicle.VehicleService;
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

    @PostMapping("/create/vehicle") // POST CREAZIONE VEICOLO
    public VehicleEntity createVehicle(@RequestBody VehicleEntity vehicle, @RequestParam String type) {
        return vehicleService.newVehicle(vehicle, type);
    }

    @GetMapping("/show/list") // GET TUTTI I VEICOLI
    public Optional<VehicleEntity> getVehicleById() {
        return null;
    }

    @GetMapping("/show/vehicle/{id}") // GET VEICOLO TRAMITE ID
    public Optional<VehicleEntity> getVehicleById(@PathVariable Long id) {
        return null;
    }
    @PutMapping("/update/vehicle/{id}") // PUT MODIFICA VEICOLO
    public Optional<VehicleEntity> updateVehicle(@PathVariable Long id, @RequestBody VehicleEntity vehicle) {
        return null;
    }

    @DeleteMapping("/delete/vehicle/{id}") // DELETE VEICOLO
    public ResponseEntity deleteVehicle(@PathVariable Long id) {
        return null;
    }




}
