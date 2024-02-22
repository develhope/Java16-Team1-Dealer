package com.develhope.spring.seller;

import com.develhope.spring.admin.AdminService;
import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.client.ClientRepository;
import com.develhope.spring.client.ClientService;
import com.develhope.spring.loginSignup.IdLogin;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderState;
import com.develhope.spring.order.OrderType;
import com.develhope.spring.rent.RentEntity;
import com.develhope.spring.rent.RentRepository;
import com.develhope.spring.rent.RentStatus;
import com.develhope.spring.seller.sellerControllerResponse.*;
import com.develhope.spring.vehicle.SellType;
import com.develhope.spring.vehicle.VehicleEntity;
import com.develhope.spring.vehicle.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class SellerService {

    @Autowired
    private IdLogin idLogin;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private AdminService adminService;

    @Autowired
    private ErrorMessageSeller errorMessageSeller;


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
        OrderEntity order = createOrder(orderEntity, idLogin.getId(), idVehicle, idClient);
        if (order != null) {
            return orderRepository.save(order);
        } else {
            return null;
        }
    }


    public OrderEntity updateStatusCancelled(Long idOrder) {
        orderRepository.updateStatusCancelledOrderWithId(idOrder);
        return orderRepository.findById(idOrder).get();
    }

    public OrderEntity updateOrder(OrderEntity orderEntity, Long idOrder) {
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

    }

    public OrderEntity checkOrder(Long id) {
        return orderRepository.findById(id).get();
    }

    public List<OrderEntity> checkAllOrdersByStatus(OrderState status) {
        return orderRepository.showListByStatus(status);

    }

    public ResponseEntity<GetVehicleByIdFromSellerResponse> getVehicleById(long id) {
        Optional<VehicleEntity> vehicle = vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            GetVehicleByIdFromSellerResponse okResponse = new GetVehicleByIdFromSellerResponse(errorMessageSeller.showFoundVehicle(id), vehicle.get());
            return ResponseEntity.status(200).body(okResponse);
        }
        GetVehicleByIdFromSellerResponse notFoundResponse = new GetVehicleByIdFromSellerResponse(errorMessageSeller.vehicleNotFound(id), null);
        return ResponseEntity.status(404).body(notFoundResponse);
    }

    public ResponseEntity<GetClientByIdFromSellerResponse> getClientById(Long id) {
        Optional<ClientEntity> client = clientRepository.findById(id);
        if (client.isPresent()) {
            GetClientByIdFromSellerResponse okResponse = new GetClientByIdFromSellerResponse(errorMessageSeller.showFoundClient(id), client.get());
            return ResponseEntity.status(200).body(okResponse);
        }
        GetClientByIdFromSellerResponse notFoundResponse = new GetClientByIdFromSellerResponse(errorMessageSeller.clientNotFound(id), null);
        return ResponseEntity.status(404).body(notFoundResponse);

    }

    public ResponseEntity<RentCreationFromSellerResponse> createRent(RentEntity rent, long clientId, long vehicleId) {
        VehicleEntity toRentVehicle = Objects.requireNonNull(getVehicleById(vehicleId).getBody()).getVehicleEntity();
        ClientEntity client = Objects.requireNonNull(getClientById(clientId).getBody()).getClientEntity();
        if ((toRentVehicle.getRentable().equals(true)) && !(toRentVehicle.getSellType().equals(SellType.ORDERABLE))) {
            RentEntity newRent = new RentEntity();

            newRent.setSellerId(sellerRepository.findById(idLogin.getId()).get());
            newRent.setClientId(client);
            newRent.setVehicleId(toRentVehicle);
            newRent.setStartingDate(rent.getStartingDate());
            newRent.setEndingDate(rent.getEndingDate());
            newRent.setDailyFee(rent.getDailyFee());
            newRent.setTotalFee(rent.getTotalFee());
            newRent.setIsPaid(rent.getIsPaid());
            newRent.setRentStatus(RentStatus.INPROGRESS);
            toRentVehicle.setRentable(false);
            rentRepository.save(newRent);

            RentCreationFromSellerResponse okResponse = new RentCreationFromSellerResponse(errorMessageSeller.rentCreation(), rent);
            return ResponseEntity.status(200).body(okResponse);
        }
        RentCreationFromSellerResponse notCreatedRentResponse = new RentCreationFromSellerResponse(errorMessageSeller.rentNotCreated(), null);
        return ResponseEntity.status(404).body(notCreatedRentResponse);
    }


    public ResponseEntity<RentUpdateFromSellerResponse> updateRent(RentEntity updatedRent, Long rentId) {
        Optional<RentEntity> toUpdateRent = rentRepository.findById(rentId);
        if (toUpdateRent.isPresent()) {

            toUpdateRent.get().setStartingDate(updatedRent.getStartingDate());
            toUpdateRent.get().setEndingDate(updatedRent.getEndingDate());
            toUpdateRent.get().setDailyFee(updatedRent.getDailyFee());
            toUpdateRent.get().setTotalFee(updatedRent.getTotalFee());
            toUpdateRent.get().setIsPaid(updatedRent.getIsPaid());
            rentRepository.saveAndFlush(toUpdateRent.get());

            RentUpdateFromSellerResponse okResponse = new RentUpdateFromSellerResponse(errorMessageSeller.rentCorrectlyUpdated(rentId), toUpdateRent.get());
            return ResponseEntity.status(200).body(okResponse);

        } else {

            RentUpdateFromSellerResponse notUpdatedRentResponse = new RentUpdateFromSellerResponse(errorMessageSeller.rentNotUpdated(rentId), null);
            return ResponseEntity.status(404).body(notUpdatedRentResponse);
        }
    }


    public ResponseEntity<RentDeletionByIdFromSellerResponse> deleteRent(Long rentToDeleteId) {
        Optional<RentEntity> toDeleteRent = rentRepository.findById(rentToDeleteId);
        LocalDateTime actualDate = LocalDateTime.now();
        if (toDeleteRent.isPresent() && (actualDate.isBefore(toDeleteRent.get().getEndingDate()))) {
            toDeleteRent.get().setRentStatus(RentStatus.DELETED);
            rentRepository.save(toDeleteRent.get());
            RentDeletionByIdFromSellerResponse okResponse = new RentDeletionByIdFromSellerResponse(errorMessageSeller.rentCorrectlyDeleted(rentToDeleteId), toDeleteRent.get());
            return ResponseEntity.status(200).body(okResponse);

        } else {
            RentDeletionByIdFromSellerResponse notDeletedResponse = new RentDeletionByIdFromSellerResponse(errorMessageSeller.rentNotDeleted(rentToDeleteId), null);
            return ResponseEntity.status(404).body(notDeletedResponse);
        }
    }

    public List<VehicleEntity> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<VehicleEntity> getAllVehiclesRfd() {
        return vehicleRepository.showAllVehiclesRfd();
    }

    public List<VehicleEntity> getAllRentableVehicles() {
        return vehicleRepository.showAllRentableVehicles();
    }

}
