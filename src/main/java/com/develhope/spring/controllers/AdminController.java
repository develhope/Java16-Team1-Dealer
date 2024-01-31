package com.develhope.spring.controllers;

import com.develhope.spring.entities.order.OrderEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @PostMapping("/create")
    public OrderEntity createOrder() {
        return new OrderEntity();
    }


}
