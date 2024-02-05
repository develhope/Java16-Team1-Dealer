package com.develhope.spring.services;

import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.repositories.ClientRepository;
import com.develhope.spring.repositories.OrderRepository;
import com.develhope.spring.repositories.UserRepository;
import com.develhope.spring.repositories.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    @Service
    public class ClientService {

        @Autowired
        private ClientRepository clientRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private VehicleRepository vehicleRepository;
        @Autowired
        private OrderRepository orderRepository;


        public OrderEntity createOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient){
            OrderEntity newOrder = new OrderEntity();
            newOrder.setOrderState(orderEntity.getOrderState());
            newOrder.setAdvPayment(orderEntity.getAdvPayment());
            newOrder.setIsPaid(orderEntity.getIsPaid());
            newOrder.setVehicleId(vehicleRepository.findById(idVehicle).get());
            newOrder.setClientId(clientRepository.findById(idClient).get());
            newOrder.setSellerId(userRepository.findById(idSeller).get());
            return newOrder;
        }

        public OrderEntity newOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
            OrderEntity order = createOrder(orderEntity, idSeller, idVehicle, idClient);
            return orderRepository.save(order);
        }



    }
