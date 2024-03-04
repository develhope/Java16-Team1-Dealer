package com.develhope.spring.seller;

import com.develhope.spring.admin.AdminService;
import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.client.ClientRepository;
import com.develhope.spring.client.ClientService;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderState;
import com.develhope.spring.order.OrderType;
import com.develhope.spring.rent.*;
import com.develhope.spring.seller.sellerControllerResponse.*;
import com.develhope.spring.user.UserEntity;
import com.develhope.spring.vehicle.SellType;
import com.develhope.spring.vehicle.VehicleEntity;
import com.develhope.spring.vehicle.VehicleRepository;
import jakarta.transaction.Transactional;
import org.hibernate.query.Order;
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

    public OrderEntity newOrder(UserEntity user, OrderEntity orderEntity, Long idClient, Long idVehicle) {
        OrderEntity order = createOrder(orderEntity, user.getId(), idVehicle, idClient); //DONE aggiungere UserEntity e prendere id e nel metodo @AuthenticationPrincipal UserEntity user
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

    public ResponseEntity<RentCreationFromSellerResponse> createRent(UserEntity user, RentDtoInput rent) {
        Optional<VehicleEntity> toRentVehicle = vehicleRepository.findById(rent.getIdVehicle());
        Optional<ClientEntity> client = clientRepository.findById(rent.getIdClient());
        if ((toRentVehicle.isPresent()) && (toRentVehicle.get().getRentable().equals(true)) && !(toRentVehicle.get().getSellType().equals(SellType.ORDERABLE)) && client.isPresent()) {
            RentEntity newRent = new RentEntity();

            newRent.setSellerId(sellerRepository.findById(user.getId()).get()); //DONE aggiungere UserEntity e prendere id e nel metodo @AuthenticationPrincipal UserEntity user
            newRent.setClientId(client.get());
            newRent.setVehicleId(toRentVehicle.get());
            newRent.setStartingDate(rent.getStartRent());
            newRent.setEndingDate(rent.getEndRent());
            newRent.setDailyFee(rent.getDailyFee());
            newRent.setTotalFee(rent.getTotalFee());
            newRent.setIsPaid(rent.getIsPaid());
            newRent.setRentStatus(RentStatus.INPROGRESS);
            toRentVehicle.get().setRentable(false);
            rentRepository.save(newRent);

            RentCreationFromSellerResponse okResponse = new RentCreationFromSellerResponse(errorMessageSeller.rentCreation(), rent);
            return ResponseEntity.status(200).body(okResponse);
        }
        RentCreationFromSellerResponse notCreatedRentResponse = new RentCreationFromSellerResponse(errorMessageSeller.rentNotCreated(), null);
        return ResponseEntity.status(404).body(notCreatedRentResponse);
    }


    public ResponseEntity<RentUpdateFromSellerResponse> updateRent(ToUpdateRentDtoInput toUpdateRentDtoInput, Long rentId) {
        Optional<RentEntity> toUpdateRent = rentRepository.findById(rentId);
        if (toUpdateRent.isPresent()) {

            toUpdateRent.get().setStartingDate(toUpdateRentDtoInput.getRentStartingDate());
            toUpdateRent.get().setEndingDate(toUpdateRentDtoInput.getRentEndingDate());
            toUpdateRent.get().setDailyFee(toUpdateRentDtoInput.getDailyFee());
            toUpdateRent.get().setTotalFee(toUpdateRentDtoInput.getTotalFee());
            toUpdateRent.get().setIsPaid(toUpdateRentDtoInput.getIsPaid());
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

    public ResponseEntity<GetAllVehiclesFromSellerResponse> getAllVehicles() {
        List<VehicleEntity> vehicles = vehicleRepository.findAll();
        if (vehicles.isEmpty()) {
            GetAllVehiclesFromSellerResponse emptyRepoResponse = new GetAllVehiclesFromSellerResponse(errorMessageSeller.noVehiclesAvailable(), null);
            return ResponseEntity.status(404).body(emptyRepoResponse);
        } else {
            GetAllVehiclesFromSellerResponse okResponse = new GetAllVehiclesFromSellerResponse(errorMessageSeller.getAllVehiclesOk(), vehicles);
            return ResponseEntity.status(200).body(okResponse);
        }

    }

    public ResponseEntity<GetVehiclesRfdFromSellerResponse> getAllVehiclesRfd() {
        List<VehicleEntity> vehicles = vehicleRepository.showAllVehiclesRfd();
        if (vehicles.isEmpty()) {
            GetVehiclesRfdFromSellerResponse noVehiclesResponse = new GetVehiclesRfdFromSellerResponse(errorMessageSeller.noVehiclesAvailable(), null);
            return ResponseEntity.status(404).body(noVehiclesResponse);
        } else {
            GetVehiclesRfdFromSellerResponse okResponse = new GetVehiclesRfdFromSellerResponse(errorMessageSeller.getAllVehiclesRfd(), vehicles);
            return ResponseEntity.status(200).body(okResponse);
        }
    }

    public ResponseEntity<GetAllRentableVehiclesFromSellerResponse> getAllRentableVehicles() {

        List<VehicleEntity> vehicles = vehicleRepository.showAllRentableVehicles();
        if (vehicles.isEmpty()) {
            GetAllRentableVehiclesFromSellerResponse notFoundResponse = new GetAllRentableVehiclesFromSellerResponse(errorMessageSeller.noVehiclesAvailable(), null);
            return ResponseEntity.status(404).body(notFoundResponse);
        } else {
            GetAllRentableVehiclesFromSellerResponse okResponse = new GetAllRentableVehiclesFromSellerResponse(errorMessageSeller.getAllRentableVehicles(), vehicles);
            return ResponseEntity.status(200).body(okResponse);
        }
    }

}
