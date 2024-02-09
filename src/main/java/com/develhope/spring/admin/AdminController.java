package com.develhope.spring.admin;

import com.develhope.spring.rent.DTOs.rentCreationDTO;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.rent.RentEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/v1/admin")
@RestController
@Tag(name = "Admin Controller", description = "Admin Controller API")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create/order/client")
    public OrderEntity createOrder(
            @RequestBody(required = true) OrderEntity order,
            @RequestParam(name = "id_seller", required = true) Long idSeller,
            @RequestParam(name = "id_vehicle", required = true) Long idVehicle,
            @RequestParam(name = "id_client", required = true) Long idClient
    ) {
        return adminService.createOrder(order, idSeller, idVehicle, idClient);
    }

    @PutMapping("/update/status/order/client/cancelled/{idOrder}")
    @ResponseBody
    public OrderEntity deleteOrder(
            @PathVariable(name = "idOrder") Long idOrder) {
        return adminService.updateStatusCancelled(idOrder);
    }

    @PatchMapping("/update/order/client/{idOrder}")
    @ResponseBody
    public OrderEntity updateOrder(
            @RequestBody OrderEntity order,
            @PathVariable(name = "idOrder") Long idOrder) {
        return adminService.updateOrder(order, idOrder);
    }

    @PostMapping("/create/client/rent")
    @ResponseBody
    public RentEntity createRent(@RequestBody rentCreationDTO rentCreationDTO){
        return adminService.createRent(rentCreationDTO);
    }

    @DeleteMapping("/delete/client/rent/{id}")
    public RentEntity deleteRent(@PathVariable Long id) {
        return adminService.deleteRent(id);
    }

    @PatchMapping("/update/client/rent/{id}")
    public RentEntity updateRent(@PathVariable Long id, @RequestBody rentCreationDTO rentCreationDTO) {
        return adminService.updateRent(id, rentCreationDTO);
    }

    @PostMapping("/create/purchase/client")
    @ResponseBody
    public OrderEntity createPurchase(
            @RequestBody(required = true) OrderEntity order,
            @RequestParam(name = "id_seller", required = true) Long idSeller,
            @RequestParam(name = "id_vehicle", required = true) Long idVehicle,
            @RequestParam(name = "id_client", required = true) Long idClient) {

        return adminService.createPurchase(order, idSeller, idVehicle, idClient);
    }

    @PutMapping("/update/status/purchase/client/cancelled/{idOrder}")
    @ResponseBody
    public OrderEntity updateStatusCancelledIdPurchase(
            @PathVariable(name = "idOrder") Long id
    ) {
        return adminService.updateStatusCancelledPurchase(id);
    }

    @PatchMapping("/update/purchase/client/{idOrder}")
    @ResponseBody
    public OrderEntity updatePurchase(
            @RequestBody OrderEntity order,
            @PathVariable(name = "idOrder") Long idOrder) {
        return adminService.updatePurchase(order, idOrder);
    }

    @GetMapping("/show/seller/orders/qty")
    public void showOrdersQtySeller() {

    }

    @GetMapping("/show/sellers/sales/period/{idSeller}")
    public String showSellerSalesInPeriodRange(@PathVariable Long idSeller, @RequestParam LocalDate first, @RequestParam LocalDate second) {
        return adminService.checkNumberOfSalesSeller(idSeller,first,second);
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
    public void showAllVehicleNotAvailable() {

    }

    @DeleteMapping("/delete/user/{id}")
    public void deleteSingleUser() {

    }

    @PatchMapping("/update/user/{id}")
    public void updateSingleUser() {

    }

    @DeleteMapping("/delete/seller/{id}")
    public void deleteSingleSeller() {

    }

    @PatchMapping("/update/seller/{id}")
    public void updateSingleSeller() {

    }

    @GetMapping("/show/vehicle/bestSeller/period")
    public void showVehicleBestSellerInPeriodRange() {

    }

    @GetMapping("/show/vehicle/maxPrice")
    public void showVehicleMaxPrice() {

    }

    @GetMapping("/show/vehicle/maxBuyer")
    public void showVehicleMaxBuyer() {

    }

}
