package com.develhope.spring.controllers;


import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.rent.RentEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/v1/seller")

public class SellerController {

    @GetMapping(path = "/getVehicleById/{id}")
    public Optional<VehicleEntity> getVehicleById(@PathVariable long id) {
        return null;
    }

    @PostMapping(path = "/createOrderFromVehicle")
    public Optional<OrderEntity> createOrderFromVehicle() {
        return null;
    }

    @DeleteMapping(path = "/deleteOrder")
    public Optional<OrderEntity> deleteOrder() {
        return null;
    }

    @PutMapping(path = "/editOrder")
    public Optional<OrderEntity> editOrder() {
        return null;
    }

    @GetMapping(path = "/checkOrder")
    public Optional<OrderEntity> checkOrder() {
        return null;
    }

    @PutMapping(path = "/updateOrder")
    public Optional<OrderEntity> updateOrder() {
        return null;
    }

    @GetMapping(path = "/checkAllOrdersByStatus")
    public List<OrderEntity> checkAllOrdersByStatus() {
        return null;
    }

    @PostMapping(path = "/createRent")
    public Optional<RentEntity> createRent() {
        return null;
    }

    @DeleteMapping(path = "/deleteRent")
    public Optional<RentEntity> deleteRent() {
        return null;
    }

    @PutMapping(path = "/editRent")
    public Optional<RentEntity> editRent() {
        return null;
    }
}
