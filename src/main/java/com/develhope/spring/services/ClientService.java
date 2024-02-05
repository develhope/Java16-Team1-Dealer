package com.develhope.spring.services;

import com.develhope.spring.dto.IdLogin;
import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.user.SellerEntity;
import com.develhope.spring.entities.user.UserEntity;
import com.develhope.spring.entities.vehicle.SellType;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private IdLogin idLogin;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private OrderRepository orderRepository;


    public OrderEntity createOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
        VehicleEntity vehicle = vehicleRepository.findById(idVehicle).get();
        if (vehicle.getSellType().equals(SellType.ORDERABLE)) {
            OrderEntity newOrder = new OrderEntity();
            newOrder.setOrderState(orderEntity.getOrderState());
            newOrder.setAdvPayment(orderEntity.getAdvPayment());
            newOrder.setIsPaid(orderEntity.getIsPaid());
            newOrder.setVehicleId(vehicleRepository.findById(idVehicle).get());
            newOrder.setClientId(clientRepository.findById(idClient).get());
            newOrder.setSellerId(sellerRepository.findById(idSeller).get());
            return newOrder;
        } else {
            return null;
        }

    }

    public OrderEntity newOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle) {
        OrderEntity order = createOrder(orderEntity, idSeller, idVehicle, idLogin.getId());
        if (order != null) {
            return orderRepository.save(order);
        } else {
            return null;
        }
    }

    public List<OrderEntity> orderEntityList() {
        return orderRepository.showListOrder(idLogin.getId());
    }

    public OrderEntity updateStatusCancelled(Long idOrder) {
        return orderRepository.updateStatusCancelledOrderWithId(idOrder);
    }


}
