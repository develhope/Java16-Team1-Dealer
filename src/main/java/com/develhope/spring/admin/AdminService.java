package com.develhope.spring.admin;

import com.develhope.spring.customer.CustomerRepository;
import com.develhope.spring.login.IdLogin;
import com.develhope.spring.rent.DTOs.rentCreationDTO;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderType;
import com.develhope.spring.rent.RentEntity;
import com.develhope.spring.rent.RentRepository;
import com.develhope.spring.shared.vehicle.VehicleRepository;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.shared.vehicle.SellType;
import com.develhope.spring.shared.vehicle.VehicleEntity;
import com.develhope.spring.seller.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@Transactional
public class AdminService {

    @Autowired
    private IdLogin idLogin;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RentRepository rentRepository;

    public OrderEntity newOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
        VehicleEntity vehicle = vehicleRepository.findById(idVehicle).get();
        if (vehicle.getSellType().equals(SellType.ORDERABLE)) {
            OrderEntity newOrder = new OrderEntity();
            newOrder.setOrderType(OrderType.ORDER);
            newOrder.setOrderState(orderEntity.getOrderState());
            newOrder.setAdvPayment(orderEntity.getAdvPayment());
            newOrder.setIsPaid(orderEntity.getIsPaid());
            newOrder.setVehicleId(vehicle);
            newOrder.setClientId(customerRepository.findById(idClient).get());
            newOrder.setSellerId(sellerRepository.findById(idSeller).get());
            return newOrder;
        } else {
            return null;
        }
    }

    public OrderEntity createOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
        if (idLogin.getType().equals("ADMIN")) {
            return orderRepository.save(newOrder(orderEntity, idSeller, idVehicle, idClient));
        } else {
            return null;
        }
    }

    public OrderEntity updateStatusCancelled(Long idOrder) {
        orderRepository.updateStatusCancelledOrderWithId(idOrder);
        return orderRepository.findById(idOrder).get();
    }

    public OrderEntity updateOrder(OrderEntity orderEntity, Long idOrder) {
        if (idLogin.getType().equals("ADMIN")) {
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
        } else {
            return null;
        }

    }

    public OrderEntity newPurchase(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
        VehicleEntity vehicle = vehicleRepository.findById(idVehicle).get();
        if (vehicle.getSellType().equals(SellType.RFD)) {
            OrderEntity newOrder = new OrderEntity();
            newOrder.setOrderType(OrderType.PURCHASE);
            newOrder.setOrderState(orderEntity.getOrderState());
            newOrder.setAdvPayment(orderEntity.getAdvPayment());
            newOrder.setIsPaid(orderEntity.getIsPaid());
            newOrder.setVehicleId(vehicle);
            newOrder.setClientId(customerRepository.findById(idClient).get());
            newOrder.setSellerId(sellerRepository.findById(idSeller).get());
            return newOrder;
        } else {
            return null;
        }
    }
    public OrderEntity createPurchase(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
        if (idLogin.getType().equals("ADMIN")) {
            return orderRepository.save(newPurchase(orderEntity, idSeller, idVehicle, idClient));
        } else {
            return null;
        }
    }

    public OrderEntity updateStatusCancelledPurchase(Long idOrder) {
        orderRepository.updateStatusCancelledPurchaseWithId(idOrder);
        return orderRepository.findById(idOrder).get();
    }

    public OrderEntity updatePurchase(OrderEntity orderEntity, Long idOrder) {
        if (idLogin.getType().equals("ADMIN")) {
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
        } else {
            return null;
        }

    }

    public RentEntity newRent(rentCreationDTO rentCreationDTO){
        VehicleEntity vehicle = vehicleRepository.findById(rentCreationDTO.getIdVehicle()).get();
        if(vehicle.getRentable()){
            RentEntity newRent = new RentEntity();
            newRent.setSellerId(sellerRepository.findById(rentCreationDTO.getIdSeller()).get());
            newRent.setClientId(customerRepository.findById(rentCreationDTO.getIdClient()).get());
            newRent.setVehicleId(vehicleRepository.findById(rentCreationDTO.getIdVehicle()).get());
            newRent.setStartingDate(rentCreationDTO.getStartRent());
            newRent.setEndingDate(rentCreationDTO.getEndRent());
            newRent.setDailyFee(rentCreationDTO.getDailyFee());
            newRent.setTotalFee(rentCreationDTO.getTotalFee());
            newRent.setIsPaid(true);
            vehicle.setRentable(false);
            return newRent;
        }else {
            return null;
        }
    }

    public RentEntity createRent(rentCreationDTO rentCreationDTO){
        return rentRepository.save(newRent(rentCreationDTO));
    }

    public RentEntity updateRent(Long idRent, rentCreationDTO rentCreationDTO){
        for(RentEntity r : rentRepository.findAll()){
            if(Objects.equals(r.getId(), idRent)) {
                if (rentCreationDTO.getIdSeller() != null) {
                    r.setSellerId(sellerRepository.findById(rentCreationDTO.getIdSeller()).get());
                }
                if (rentCreationDTO.getIdClient() != null) {
                    r.setClientId(customerRepository.findById(rentCreationDTO.getIdClient()).get());
                }
                if (rentCreationDTO.getIdVehicle() != null) {
                    r.setVehicleId(vehicleRepository.findById(rentCreationDTO.getIdVehicle()).get());
                }
                if (rentCreationDTO.getEndRent() != null) {
                    r.setEndingDate(rentCreationDTO.getEndRent());
                }
                if (rentCreationDTO.getDailyFee() != null) {
                    r.setDailyFee(rentCreationDTO.getDailyFee());
                }
                if (rentCreationDTO.getTotalFee() != null) {
                    r.setTotalFee(rentCreationDTO.getTotalFee());
                }
                return rentRepository.save(r);
            }
        }
        return null;
    }

    public RentEntity deleteRent(Long id){
        for(RentEntity r : rentRepository.findAll()){
            if(r.getId().equals(id)){
                rentRepository.deleteById(r.getId());
                return r;
            }
        }
        return null;
    }

    public String checkNumberOfSalesSeller(Long idSeller, LocalDate firstDate, LocalDate secondDate){
        for(SellerEntity s : sellerRepository.findAll()){
            if(s.getId()==idSeller){
                return "Number of purchase " + orderRepository.checkNumberOfSalesSeller(s.getId(),firstDate,secondDate);
            }
        }
        return null;
    }


}
