package com.develhope.spring.services;

import com.develhope.spring.dto.IdLogin;
import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.order.OrderState;
import com.develhope.spring.entities.order.OrderType;
import com.develhope.spring.entities.vehicle.SellType;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SellerService {

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
            newOrder.setOrderType(OrderType.ORDER);
            newOrder.setOrderState(orderEntity.getOrderState());
            newOrder.setAdvPayment(orderEntity.getAdvPayment());
            newOrder.setIsPaid(orderEntity.getIsPaid());
            newOrder.setVehicleId(vehicle);
            newOrder.setClientId(clientRepository.findById(idClient).get());
            newOrder.setSellerId(sellerRepository.findById(idSeller).get());
            return newOrder;
        } else {
            return null;
        }

    }

    public OrderEntity newOrder(OrderEntity orderEntity, Long idClient, Long idVehicle) {
        if (idLogin.getType().equals("SELLER")) {
            OrderEntity order = createOrder(orderEntity, idLogin.getId(), idVehicle, idClient);
            if (order != null) {
                return orderRepository.save(order);
            } else {
                return null;
            }
        }
        return null;
    }


    public OrderEntity updateStatusCancelled(Long idOrder) {
        orderRepository.updateStatusCancelledOrderWithId(idOrder);
        return orderRepository.findById(idOrder).get();
    }

    public OrderEntity updateOrder(OrderEntity orderEntity, Long idOrder) {
        if (idLogin.getType().equals("SELLER")) {
            OrderEntity order = orderRepository.findById(idOrder).get();
            if (orderEntity.getOrderType() != null) {
                order.setOrderType(orderEntity.getOrderType());
            }
            if (orderEntity.getOrderState() != null) {
                order.setOrderState(orderEntity.getOrderState());
            }
            if (orderEntity.getAdvPayment() != null) {
                order.setAdvPayment(orderEntity.getAdvPayment());
            }
            return orderRepository.save(order);
        }else {
            return null;
        }

    }

    public OrderEntity checkOrder(Long id) {
        if (idLogin.getType().equals("SELLER")) {
            return orderRepository.findById(id).get();
        } else {
            return null;
        }
    }

    public List<OrderEntity> checkAllOrdersByStatus(OrderState status) {
        if(idLogin.getType().equals("SELLER")){
            return orderRepository.showListByStatus(status);
        }else{
            return null;
        }
    }



}
