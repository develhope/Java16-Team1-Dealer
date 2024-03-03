package com.develhope.spring;

import com.develhope.spring.admin.AdminRepository;
import com.develhope.spring.admin.AdminService;
import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.client.ClientRepository;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderState;
import com.develhope.spring.order.OrderType;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.seller.SellerRepository;
import com.develhope.spring.user.UserEntity;
import com.develhope.spring.user.UserRepository;
import com.develhope.spring.user.UserType;
import com.develhope.spring.vehicle.GearType;
import com.develhope.spring.vehicle.SellType;
import com.develhope.spring.vehicle.VehicleEntity;
import com.develhope.spring.vehicle.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(value = {"classpath:application-test.properties"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void contextLoads() {
    }

    private VehicleEntity createVehicleOrderable() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setBrand("Fiat");
        vehicle.setModel("500L");
        vehicle.setEngineCapacity(1300);
        vehicle.setColour("Black");
        vehicle.setHp(90);
        vehicle.setGearType(GearType.MANUAL);
        vehicle.setRegisterYear(LocalDate.of(2022, 01, 01));
        vehicle.setFuelType("Diesel");
        vehicle.setPrice(BigDecimal.valueOf(30000));
        vehicle.setPriceDscnt(5);
        vehicle.setAccessories("full optional");
        vehicle.setRentable(true);
        vehicle.setSellType(SellType.ORDERABLE);
        return vehicle;
    }

    private VehicleEntity createVehicleUsed() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setBrand("Fiat");
        vehicle.setModel("500L");
        vehicle.setEngineCapacity(1300);
        vehicle.setColour("Black");
        vehicle.setHp(90);
        vehicle.setGearType(GearType.MANUAL);
        vehicle.setRegisterYear(LocalDate.of(2022, 01, 01));
        vehicle.setFuelType("Diesel");
        vehicle.setPrice(BigDecimal.valueOf(30000));
        vehicle.setPriceDscnt(5);
        vehicle.setAccessories("full optional");
        vehicle.setRentable(true);
        vehicle.setSellType(SellType.USED);
        return vehicle;
    }

    private ClientEntity createClient() {
        ClientEntity client = new ClientEntity();
        client.setName("Anto");
        client.setSurname("Troiano");
        client.setEmail("anto@gmail.com");
        client.setPsw("anto");
        client.setType(UserType.CLIENT);
        return client;
    }

    private SellerEntity createSeller() {
        SellerEntity seller = new SellerEntity();
        seller.setName("Pino");
        seller.setSurname("Franco");
        seller.setEmail("pin8@gmail.com");
        seller.setPsw("pino");
        seller.setType(UserType.SELLER);
        return seller;

    }


    @Test
    void newOrder() {
        createVehicleOrderable();
        createSeller();
        createClient();
        vehicleRepository.save(createVehicleOrderable());
        sellerRepository.save(createSeller());
        clientRepository.save(createClient());

        VehicleEntity vehicle = vehicleRepository.findById(1L).get();
        SellerEntity seller = sellerRepository.findById(1L).get();
        ClientEntity clientEntity = clientRepository.findById(2L).get();


        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderType(OrderType.ORDER);
        newOrder.setOrderState(OrderState.DELIVERED);
        newOrder.setAdvPayment(BigDecimal.valueOf(10000));
        newOrder.setIsPaid(true);
        newOrder.setVehicleId(vehicle);
        newOrder.setClientId(clientEntity);
        newOrder.setSellerId(seller);

        assertThat(adminService.newOrder(newOrder, seller.getId(), vehicle.getId(), clientEntity.getId())).isNotNull();
    }

    @Test
    void newOrderWithNotOrderableVehicle() {

        createClient();
        createSeller();
        createVehicleUsed();
        vehicleRepository.save(createVehicleUsed());
        sellerRepository.save(createSeller());
        clientRepository.save(createClient());

        VehicleEntity vehicle = vehicleRepository.findById(1L).get();
        SellerEntity seller = sellerRepository.findById(1L).get();
        ClientEntity client = clientRepository.findById(2L).get();

        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderType(OrderType.ORDER);
        newOrder.setOrderState(OrderState.DELIVERED);
        newOrder.setAdvPayment(BigDecimal.valueOf(10000));
        newOrder.setIsPaid(true);
        newOrder.setVehicleId(vehicle);
        newOrder.setClientId(client);
        newOrder.setSellerId(seller);

        OrderEntity order = adminService.newOrder(newOrder,seller.getId(),vehicle.getId(),client.getId());

        assertNull(order);

    }

    @Test
    void createOrder(){
        createClient();
        createSeller();
        createVehicleOrderable();
        vehicleRepository.save(createVehicleOrderable());
        sellerRepository.save(createSeller());
        clientRepository.save(createClient());

        VehicleEntity vehicle = vehicleRepository.findById(1L).get();
        SellerEntity seller = sellerRepository.findById(1L).get();
        ClientEntity client = clientRepository.findById(2L).get();

        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderType(OrderType.ORDER);
        newOrder.setOrderState(OrderState.DELIVERED);
        newOrder.setAdvPayment(BigDecimal.valueOf(10000));
        newOrder.setIsPaid(true);
        newOrder.setVehicleId(vehicle);
        newOrder.setClientId(client);
        newOrder.setSellerId(seller);

        ResponseEntity<String> orderResponse = adminService.createOrder(newOrder,seller.getId(),vehicle.getId(),client.getId());

        assertThat(orderResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(orderResponse.getBody()).isEqualTo("Order successfully created");
    }
}
