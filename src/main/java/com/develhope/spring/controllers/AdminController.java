package com.develhope.spring.controllers;

import com.develhope.spring.entities.order.OrderEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @PostMapping("/create/client/order")
    public OrderEntity createOrder() {
        return new OrderEntity();
    }

    @DeleteMapping("/delete/client/order")
    public void deleteOrder() {
    }

    @PatchMapping("/update/client/order")
    public OrderEntity updateOrder() {
        return new OrderEntity();
    }

    @PostMapping("/create/client/rent")
    public void createRent() {
    }

    @DeleteMapping("/delete/client/rent")
    public void deleteRent() {
    }

    @PatchMapping("/update/client/rent")
    public OrderEntity updateRent() {
        return new OrderEntity();
    }

    @PostMapping("/create/client/purchase")
    public void createPurchase() {
    }

    @DeleteMapping("/delete/client/purchase")
    public void deletePurchase() {
    }

    @PatchMapping("/update/client/purchase")
    public OrderEntity updatePurchase() {
        return new OrderEntity();
    }

    @GetMapping("/show/seller/orders/qty")
    public void showOrdersQtySeller() {

    }

    @GetMapping("/show/sellers/sales/period")
    public void showSellerSalesInPeriodRange() {

    }

    @GetMapping("/show/allSales/period")
    public void showAllSalesInPerioRange() {

    }

    @GetMapping("/show/vehicle/orderable")
    public void showAvailableVehicleOrderable() {

    }

    @GetMapping("/show/vehicle/rfdNew")
    public void showAvailableVehiclerfdNew() {

    }

    @GetMapping("/show/vehicle/rfdUsed")
    public void showAvailableVehiclerfdUsed() {

    }

    @GetMapping("/show/vehicle/used")
    public void showAvailableVehicleused() {

    }

    @GetMapping("/show/vehicle/notAvaiable")
    public void showAllVehicleNotAvailable(){

    }

    @DeleteMapping("/delete/user/{id}")
    public void deleteSingleUser(){

    }

    @PatchMapping("/update/user/{id}")
    public void updateSingleUser(){

    }

    @DeleteMapping("/delete/seller/{id}")
    public void deleteSingleSeller(){

    }

    @PatchMapping("/update/seller/{id}")
    public void updateSingleSeller(){

    }

    @GetMapping("/show/vehicle/bestSeller/period")
    public void showVehicleBestSellerInPeriodRange(){

    }
}
