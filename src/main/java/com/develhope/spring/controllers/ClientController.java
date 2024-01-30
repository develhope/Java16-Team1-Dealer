package com.develhope.spring.controllers;


import ch.qos.logback.core.net.server.Client;
import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.rent.RentEntity;
import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import org.hibernate.query.Order;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@RestController
@RequestMapping("/v1")
public class ClientController {

    @PostMapping("/neworder")
    public @ResponseBody OrderEntity newOrder(@RequestBody VehicleEntity orderableVehicle) {
        return new OrderEntity();
    }

    @GetMapping("/myorders/{id}")
    public void showOrders(@PathVariable long id) {
    }

    @DeleteMapping("/delorder/{id}")
    public void deleteOrder(@PathVariable long id) {
    }

    @PostMapping("/newpurchase")
    public void newPurchase(@RequestBody VehicleEntity purchasableVehicle) {
    }

    @GetMapping("/mypurchases/{id}")
    public void showPurchases(@PathVariable long id) {
    }

    @PostMapping("/newrent")
    public @ResponseBody RentEntity newRent (@RequestBody RentEntity rent) {
        return new RentEntity();
    }

    @GetMapping("/myrents/{id}")
    public void showRents (@PathVariable long id) {
    }

    @DeleteMapping("/delrent/{id}")
    public void deleteRent(@PathVariable long id) {
    }

    @DeleteMapping("/delclient/{id}")
    public void deleteClient(@PathVariable long id) {
    }

    @PutMapping("/updclient/{id}")
    public @ResponseBody ClientEntity updateClient (@PathVariable long id, @RequestBody ClientEntity updClient) {
    return new ClientEntity();
    }

    @GetMapping("/findvehicle")
    public @ResponseBody VehicleEntity showVehicle() {
        return new VehicleEntity();
    }


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
/*

    Un cliente potrà:

        Creare un ordine a partire da un veicolo contrassegnato come ordinabile
        Vedere i propri ordini
        Cancellare un ordine
        Creare un acquisto a partire da un veicolo contrassegnato come acquistabile
        Vedere i propri acquisti
        Creare un noleggio
        Vedere i propri noleggi
        Cancellare un noleggio
        Cancellare la propria utenza
        Modificare i dati dell’utente
        Ricercare un veicolo secondo diversi criteri (prezzo, colore, marca, modello, ecc)
        Ottenere i dettagli di un veicolo specifico*/
