package com.develhope.spring.controllers;

import com.develhope.spring.entities.vehicle.VehicleEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/vehicles")
public class VehicleController {

    @PostMapping("/create/vehicle") // POST CREAZIONE VEICOLO
    public Optional<VehicleEntity> createVehicle(@RequestBody VehicleEntity vehicle, @RequestParam String type) {
        return null;
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
