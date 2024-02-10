package com.develhope.spring.admin;

import com.develhope.spring.admin.adminControllerResponse.*;
import com.develhope.spring.client.*;
import com.develhope.spring.loginSignup.*;
import com.develhope.spring.order.*;
import com.develhope.spring.rent.*;
import com.develhope.spring.seller.*;
import com.develhope.spring.user.UserType;
import com.develhope.spring.vehicle.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class AdminService {

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
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private ErrorMessagesAdmin errorMessagesAdmin;

    public OrderEntity newOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
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
            newOrder.setClientId(clientRepository.findById(idClient).get());
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

    public ResponseEntity<UpdateClientbyAdminResponse> updateClientbyAdmin(ClientEntity clientEntity, Long idClient) {
        ClientEntity client = new ClientEntity();
        if (clientRepository.existsById(idClient)) {
             client = clientRepository.findById(idClient).get();
            if (client.getType() == UserType.CLIENT) {
                if (clientEntity.getName() != null) {
                    client.setName(clientEntity.getName());
                }
                if (clientEntity.getSurname() != null) {
                    client.setSurname(clientEntity.getSurname());
                }
                if (clientEntity.getEmail() != null) {
                    boolean equals = false;
                    for(ClientEntity c : clientRepository.findAll()) {
                        if(c.getEmail().equals(clientEntity.getEmail())){
                            equals = true;
                        }
                    }
                    if(!equals){
                        client.setEmail(clientEntity.getEmail());
                    }else {
                        UpdateClientbyAdminResponse updateClientbyAdminResponse = new UpdateClientbyAdminResponse(errorMessagesAdmin.updateClientbyAdminEmailExist(clientEntity.getEmail()), new ClientEntity());
                        return ResponseEntity.status(514).body(updateClientbyAdminResponse);
                    }
                }
                if (clientEntity.getPhone() != null) {
                    client.setPhone(clientEntity.getPhone());
                }
                if (clientEntity.getPsw() != null) {
                    client.setPsw(clientEntity.getPsw());
                }
                clientRepository.save(client);
                UpdateClientbyAdminResponse updateClientbyAdminResponse = new UpdateClientbyAdminResponse(errorMessagesAdmin.updateClientbyAdminOK(idClient), client);
                return ResponseEntity.status(513).body(updateClientbyAdminResponse);
            }else {
                UpdateClientbyAdminResponse updateClientbyAdminResponse = new UpdateClientbyAdminResponse(errorMessagesAdmin.updateClientbyAdminNotFoundClient(idClient), client);
                return ResponseEntity.status(512).body(updateClientbyAdminResponse);
            }
        }else {
            UpdateClientbyAdminResponse updateClientbyAdminResponse = new UpdateClientbyAdminResponse(errorMessagesAdmin.updateClientbyAdminNotFoundClient(idClient), client);
            return ResponseEntity.status(404).body(updateClientbyAdminResponse);
        }
    }

    public TruckEntity createTruck(VehicleEntity vehicle) {
        TruckEntity truck = new TruckEntity();
        truck.setBrand(vehicle.getBrand());
        truck.setModel(vehicle.getModel());
        truck.setEngineCapacity(vehicle.getEngineCapacity());
        truck.setColour(vehicle.getColour());
        truck.setHp(vehicle.getHp());
        truck.setGearType(vehicle.getGearType());
        truck.setRegisterYear(vehicle.getRegisterYear());
        truck.setFuelType(vehicle.getFuelType());
        truck.setPrice(vehicle.getPrice());
        truck.setPriceDscnt(vehicle.getPriceDscnt());
        truck.setAccessories(vehicle.getAccessories());
        truck.setRentable(vehicle.getRentable());
        truck.setSellType(vehicle.getSellType());
        return truck;
    }

    public AutoEntity createAuto(VehicleEntity vehicle) {
        AutoEntity auto = new AutoEntity();
        auto.setBrand(vehicle.getBrand());
        auto.setModel(vehicle.getModel());
        auto.setEngineCapacity(vehicle.getEngineCapacity());
        auto.setColour(vehicle.getColour());
        auto.setHp(vehicle.getHp());
        auto.setGearType(vehicle.getGearType());
        auto.setRegisterYear(vehicle.getRegisterYear());
        auto.setFuelType(vehicle.getFuelType());
        auto.setPrice(vehicle.getPrice());
        auto.setPriceDscnt(vehicle.getPriceDscnt());
        auto.setAccessories(vehicle.getAccessories());
        auto.setRentable(vehicle.getRentable());
        auto.setSellType(vehicle.getSellType());
        return auto;
    }

    public ScooterEntity createScooter(VehicleEntity vehicle) {
        ScooterEntity scooter = new ScooterEntity();
        scooter.setBrand(vehicle.getBrand());
        scooter.setModel(vehicle.getModel());
        scooter.setEngineCapacity(vehicle.getEngineCapacity());
        scooter.setColour(vehicle.getColour());
        scooter.setHp(vehicle.getHp());
        scooter.setGearType(vehicle.getGearType());
        scooter.setRegisterYear(vehicle.getRegisterYear());
        scooter.setFuelType(vehicle.getFuelType());
        scooter.setPrice(vehicle.getPrice());
        scooter.setPriceDscnt(vehicle.getPriceDscnt());
        scooter.setAccessories(vehicle.getAccessories());
        scooter.setRentable(vehicle.getRentable());
        scooter.setSellType(vehicle.getSellType());
        return scooter;
    }

    public MotoEntity createMoto(VehicleEntity vehicle) {
        MotoEntity moto = new MotoEntity();
        moto.setBrand(vehicle.getBrand());
        moto.setModel(vehicle.getModel());
        moto.setEngineCapacity(vehicle.getEngineCapacity());
        moto.setColour(vehicle.getColour());
        moto.setHp(vehicle.getHp());
        moto.setGearType(vehicle.getGearType());
        moto.setRegisterYear(vehicle.getRegisterYear());
        moto.setFuelType(vehicle.getFuelType());
        moto.setPrice(vehicle.getPrice());
        moto.setPriceDscnt(vehicle.getPriceDscnt());
        moto.setAccessories(vehicle.getAccessories());
        moto.setRentable(vehicle.getRentable());
        moto.setSellType(vehicle.getSellType());
        return moto;
    }




    public ResponseEntity<CreateVehicleAdminResponse> newVehicle(VehicleEntity vehicle, String type) {
        switch (type.toLowerCase()) {
            case "truck":
                CreateVehicleAdminResponse createTruck = new CreateVehicleAdminResponse(errorMessagesAdmin.createTruckAdminOK(), vehicleRepository.save(createTruck(vehicle)));
                return ResponseEntity.status(201).body(createTruck);
            case "moto":
                CreateVehicleAdminResponse createMoto = new CreateVehicleAdminResponse(errorMessagesAdmin.createMotoAdminOK(), vehicleRepository.save(createMoto(vehicle)));
                return ResponseEntity.status(201).body(createMoto);
            case "auto":
                CreateVehicleAdminResponse createCar = new CreateVehicleAdminResponse(errorMessagesAdmin.createCarAdminOK(), vehicleRepository.save(createAuto(vehicle)));
                return ResponseEntity.status(201).body(createCar);
            case "scooter":
                CreateVehicleAdminResponse createScooter = new CreateVehicleAdminResponse(errorMessagesAdmin.createScooterAdminOK(), vehicleRepository.save(createScooter(vehicle)));
                return ResponseEntity.status(201).body(createScooter);
            default:
                return null;
        }

    }

    public ResponseEntity<ShowListVehicleAdminResponse> showVehicles() {
        if(vehicleRepository.findAll().size() > 0){
            List<VehicleEntity> vehicles = vehicleRepository.findAll();
            ShowListVehicleAdminResponse showListVehicleAdminResponse = new ShowListVehicleAdminResponse(errorMessagesAdmin.listVehiclesAdminOK(vehicles.size()), vehicles);
            return ResponseEntity.status(200).body(showListVehicleAdminResponse);
        }else{
            ShowListVehicleAdminResponse showListVehicleAdminResponse = new ShowListVehicleAdminResponse(errorMessagesAdmin.listVehiclesAdminEmpty(), Arrays.asList());
            return ResponseEntity.status(404).body(showListVehicleAdminResponse);
        }

    }

    public ResponseEntity<ShowVehicleAdminResponse> showVehiclebyId(Long idVehicle) {
        VehicleEntity vehicle = new VehicleEntity();
        if(vehicleRepository.existsById(idVehicle)){
            vehicle = vehicleRepository.findById(idVehicle).get();
            ShowVehicleAdminResponse vehicleExist = new ShowVehicleAdminResponse(errorMessagesAdmin.vehicleExist(), vehicle);
           return  ResponseEntity.status(200).body(vehicleExist);
        }else{
            ShowVehicleAdminResponse vehicleNotExist = new ShowVehicleAdminResponse(errorMessagesAdmin.vehicleNotExist(), vehicle);
            return ResponseEntity.status(404).body(vehicleNotExist);
        }
    }






    public RentEntity newRent(RentDto rentDto) {
        VehicleEntity vehicle = vehicleRepository.findById(rentDto.getIdVehicle()).get();
        if (vehicle.getRentable()) {
            RentEntity newRent = new RentEntity();
            newRent.setSellerId(sellerRepository.findById(rentDto.getIdSeller()).get());
            newRent.setClientId(clientRepository.findById(rentDto.getIdClient()).get());
            newRent.setVehicleId(vehicleRepository.findById(rentDto.getIdVehicle()).get());
            newRent.setStartingDate(rentDto.getStartRent());
            newRent.setEndingDate(rentDto.getEndRent());
            newRent.setDailyFee(rentDto.getDailyFee());
            newRent.setTotalFee(rentDto.getTotalFee());
            newRent.setIsPaid(true);
            vehicle.setRentable(false);
            return newRent;
        } else {
            return null;
        }
    }

    public RentEntity createRent(RentDto rentDto) {
        return rentRepository.save(newRent(rentDto));
    }

    public RentEntity updateRent(Long idRent, RentDto rentDto) {
        for (RentEntity r : rentRepository.findAll()) {
            if (Objects.equals(r.getId(), idRent)) {
                if (rentDto.getIdSeller() != null) {
                    r.setSellerId(sellerRepository.findById(rentDto.getIdSeller()).get());
                }
                if (rentDto.getIdClient() != null) {
                    r.setClientId(clientRepository.findById(rentDto.getIdClient()).get());
                }
                if (rentDto.getIdVehicle() != null) {
                    r.setVehicleId(vehicleRepository.findById(rentDto.getIdVehicle()).get());
                }
                if (rentDto.getEndRent() != null) {
                    r.setEndingDate(rentDto.getEndRent());
                }
                if (rentDto.getDailyFee() != null) {
                    r.setDailyFee(rentDto.getDailyFee());
                }
                if (rentDto.getTotalFee() != null) {
                    r.setTotalFee(rentDto.getTotalFee());
                }
                return rentRepository.save(r);
            }
        }
        return null;
    }

    public RentEntity deleteRent(Long id) {
        for (RentEntity r : rentRepository.findAll()) {
            if (r.getId().equals(id)) {
                rentRepository.deleteById(r.getId());
                return r;
            }
        }
        return null;
    }

    public String checkNumberOfSalesSeller(Long idSeller, LocalDate firstDate, LocalDate secondDate) {
        for (SellerEntity s : sellerRepository.findAll()) {
            if (s.getId() == idSeller) {
                return "Number of purchase " + orderRepository.checkNumberOfSalesSeller(s.getId(), firstDate, secondDate);
            }
        }
        return null;
    }


}
