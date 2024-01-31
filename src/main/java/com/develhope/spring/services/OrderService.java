package com.develhope.spring.services;

import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;;



    public List<OrderEntity> orderListIdClient(Long idClient) {
        return orderRepository.orderListIdClient(idClient);
    }




}
