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

    @GetMapping(path = "/show/vehicle/by/{id}")
    public Optional<VehicleEntity> getVehicleById(@PathVariable long id) {
        return null;
    }

    @PostMapping(path = "/create/order/from/vehicle")
    public Optional<OrderEntity> createOrderFromVehicle() {
        return null;
    }

    @DeleteMapping(path = "/delete/order")
    public Optional<OrderEntity> deleteOrder() {
        return null;
    }

    @PatchMapping(path = "/update/order")
    public Optional<OrderEntity> editOrder() {
        return null;
    }

    @GetMapping(path = "/show/order/by/{id}")
    public Optional<OrderEntity> checkOrder(@PathVariable long id) {
        return null;
    }

    @PatchMapping(path = "/update/order/by/{id}")
    public Optional<OrderEntity> updateOrder() {
        return null;
    }

    @GetMapping(path = "/check/all/orders/by/status")
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

    @PatchMapping(path = "/update/rent")
    public Optional<RentEntity> editRent() {
        return null;
    }
}
