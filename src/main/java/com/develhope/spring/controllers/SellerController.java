package com.develhope.spring.controllers;


import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.order.OrderState;
import com.develhope.spring.entities.rent.RentEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/v1/seller")

public class SellerController {

    @Autowired
    private SellerService sellerService;


    @GetMapping(path = "/show/vehicle/id/{id}")
    public Optional<VehicleEntity> getVehicleById(@PathVariable Long id) {
        return sellerService.getVehicleById(id);
    }

    @PostMapping(path = "/create/order")
    @ResponseBody
    public OrderEntity newOrder(
            @RequestBody(required = true) OrderEntity order,
            @RequestParam(name = "id_client", required = true) Long idClient,
            @RequestParam(name = "id_vehicle", required = true) Long idVehicle) {
        return sellerService.newOrder(order, idClient, idVehicle);
    }

    @PutMapping(path = "/update/status/order/cancelled/{idOrder}")
    @ResponseBody
    public OrderEntity deleteOrder(
            @PathVariable(name = "idOrder") Long id) {
        return sellerService.updateStatusCancelled(id);
    }

    @PatchMapping(path = "/update/order/{idOrder}")
    public OrderEntity editOrder(
            @PathVariable(name = "idOrder") Long idOrder,
            @RequestBody OrderEntity order) {

        return sellerService.updateOrder(order, idOrder);
    }

    @GetMapping(path = "/show/order/{id}")
    public OrderEntity checkOrder(@PathVariable(name = "id") Long id) {
        return sellerService.checkOrder(id);
    }

    @GetMapping(path = "/show/order/list/by/status")
    public List<OrderEntity> checkAllOrdersByStatus(@RequestParam(name = "status") OrderState status) {
        return sellerService.checkAllOrdersByStatus(status);
    }

    @PostMapping(path = "/create/rent")
    public RentEntity createRent(
            @RequestBody(required = true) RentEntity rent,
            @RequestParam(name = "client_id", required = true) long idClient,
            @RequestParam(name = "vehicle_id", required = true) long idVehicle) {
        return sellerService.newRent(rent, idClient, idVehicle);
    }

    @DeleteMapping(path = "/delete/rent/{rentId}")
    public Boolean deleteRent(@PathVariable(name = "rentId") long id) {
        return sellerService.deleteRent(id);
    }

    @PatchMapping(path = "/update/rent/{rentId}")
    public RentEntity editRent(
            @PathVariable(name = "rentId") long id,
            @RequestBody RentEntity updatedRent) {
        return sellerService.updateRent(updatedRent, id);
    }

    @GetMapping(path = "/show/all/vehicles")
    public List<VehicleEntity> getAllVehicles() {
        return sellerService.getAllVehicles();
    }

    @GetMapping(path = "/show/all/vehicles/rfd")
    public List<VehicleEntity> getAllVehiclesRfd() {
        return sellerService.getAllVehiclesRfd();
    }

    @GetMapping(path = "/show/all/rentable/vehicles")
    public List<VehicleEntity> getAllRentableVehicles() {
        return sellerService.getAllRentableVehicles();
    }

}
