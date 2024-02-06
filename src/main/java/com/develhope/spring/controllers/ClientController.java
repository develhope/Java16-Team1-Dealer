package com.develhope.spring.controllers;

import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.rent.RentEntity;
import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/create/order")
    @ResponseBody
    public OrderEntity newOrder(
            @RequestBody(required = true) OrderEntity order,
            @RequestParam(name = "id_seller" , required = true) Long idSeller,
            @RequestParam(name = "id_vehicle", required = true) Long idVehicle) {

        return clientService.newOrder(order, idSeller, idVehicle);
    }

    @GetMapping("/show/order/list")
    public @ResponseBody List<OrderEntity> orderEntityList() {
        return clientService.orderEntityList();
    }

    @PutMapping("/update/status/order/cancelled/{idOrder}")
    public @ResponseBody OrderEntity updateStatusCancelledId(@PathVariable(name = "idOrder" ) Long id) {
        return clientService.updateStatusCancelled(id);
    }

    @PostMapping("/create/purchase")
    @ResponseBody
    public OrderEntity createPurchase(
            @RequestBody(required = true) OrderEntity order,
            @RequestParam(name = "id_seller" , required = true) Long idSeller,
            @RequestParam(name = "id_vehicle", required = true) Long idVehicle) {
        return clientService.newPurchase(order, idSeller, idVehicle);
    }

    @GetMapping("/show/purchase/list")
    public @ResponseBody List<OrderEntity> showPurchases() {
        return clientService.purchaseList();
    }

    @PostMapping("/create/rent")
    @ResponseBody
    public RentEntity newRent (@RequestBody(required = true) RentEntity rent,
                               @RequestParam(name = "id_seller", required = true) Long idSeller,
                               @RequestParam(name = "id_client", required = true) Long idClient,
                               @RequestParam(name = "id_vehicle", required = true) Long idVehicle) {
        return clientService.newRent(rent, idSeller, idClient, idVehicle);
    }
    @GetMapping("/show/rent/list")
    public @ResponseBody List<RentEntity> showRents () {
        return clientService.showRents();
    }

    @DeleteMapping("/delete/rent/{id}")
    public void deleteRent(@PathVariable Long id) {
        clientService.deleteRent(id);
    }

    @DeleteMapping("/delete/myaccount")
    public void deleteClient(@PathVariable Long id) {
    }

    @PutMapping("/upgrade/myaccount")
    public @ResponseBody ClientEntity updateClient (@PathVariable Long id, @RequestBody ClientEntity updClient) {
    return new ClientEntity();
    }

    @GetMapping("/show/list/vehicle/by/filter")
    public @ResponseBody VehicleEntity showVehicle() {
        return new VehicleEntity();
    }

    // DA RIVEDERE NON LO GESTIREI IN QUESTO MODO
    @GetMapping("/findbyprice")
    public void findVehicleByPrice() {
    }

    @GetMapping("/findbycolor")
    public void findVehicleByColor() {
    }

    @GetMapping("/findbymodel")
    public void findVehicleByModel() {
    }

    @GetMapping("/findbybrand")
    public void findVehicleByBrand() {
    }

}

