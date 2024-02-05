package com.develhope.spring.controllers;


import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.rent.RentEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/v1/seller")

public class SellerController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/show/vehicle/by/{id}")
    public Optional<VehicleEntity> getVehicleById(@PathVariable Long id) {
        return null;
    }

    @PostMapping(path = "/create/order")
    public Optional<OrderEntity> createOrderFromVehicle() {
        return null;
    }

    @DeleteMapping(path = "/delete/order/{id}")
    public Optional<OrderEntity> deleteOrder() {
        return null;
    }

    @PatchMapping(path = "/update/order/{id}")
    public Optional<OrderEntity> editOrder() {
        return null;
    }

    @GetMapping(path = "/show/order/{id}")
    public Optional<OrderEntity> checkOrder(@PathVariable Long id) {
        return null;
    }

    @GetMapping(path = "/show/order/list/by/status")
    public List<OrderEntity> checkAllOrdersByStatus() {
        return null;
    }

    @PostMapping(path = "/create/rent")
    public Optional<RentEntity> createRent() {
        return null;
    }

    @DeleteMapping(path = "/delete/rent")
    public Optional<RentEntity> deleteRent() {
        return null;
    }

    @PatchMapping(path = "/update/rent/{id}")
    public Optional<RentEntity> editRent() {
        return null;
    }
}
