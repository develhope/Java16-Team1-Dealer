package com.develhope.spring;

import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.client.ClientRepository;
import com.develhope.spring.rent.RentEntity;
import com.develhope.spring.rent.RentRepository;
import com.develhope.spring.rent.RentStatus;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.seller.SellerRepository;
import com.develhope.spring.seller.SellerService;
import com.develhope.spring.seller.sellerControllerResponse.GetClientByIdFromSellerResponse;
import com.develhope.spring.seller.sellerControllerResponse.GetVehicleByIdFromSellerResponse;
import com.develhope.spring.seller.sellerControllerResponse.RentCreationFromSellerResponse;
import com.develhope.spring.user.UserEntity;
import com.develhope.spring.user.UserRepository;
import com.develhope.spring.user.UserType;
import com.develhope.spring.vehicle.GearType;
import com.develhope.spring.vehicle.SellType;
import com.develhope.spring.vehicle.VehicleEntity;
import com.develhope.spring.vehicle.VehicleRepository;
import com.mysql.cj.xdevapi.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(value = {"classpath:application-test.properties"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class SellerServiceTest {
    //    REPOS:
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private RentRepository rentRepository;


    //    SERVICES:
    @Autowired
    private SellerService sellerService;

    public VehicleEntity createAndSaveOrderableVehicle() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setBrand("Audi");
        vehicle.setModel("RS6");
        vehicle.setEngineCapacity(5000);
        vehicle.setColour("Cosmo black");
        vehicle.setHp(840);
        vehicle.setGearType(GearType.AUTOMATIC);
        vehicle.setRegisterYear(LocalDate.now());
        vehicle.setFuelType("BENZ");
        vehicle.setPrice(BigDecimal.valueOf(150000));
        vehicle.setPriceDscnt(3);
        vehicle.setAccessories("Full-optional");
        vehicle.setRentable(false);
        vehicle.setSellType(SellType.ORDERABLE);
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public VehicleEntity createAndSaveRentableVehicle() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setBrand("Audi");
        vehicle.setModel("RS6");
        vehicle.setEngineCapacity(5000);
        vehicle.setColour("Cosmo black");
        vehicle.setHp(840);
        vehicle.setGearType(GearType.AUTOMATIC);
        vehicle.setRegisterYear(LocalDate.now());
        vehicle.setFuelType("BENZ");
        vehicle.setPrice(BigDecimal.valueOf(150000));
        vehicle.setPriceDscnt(3);
        vehicle.setAccessories("Full-optional");
        vehicle.setRentable(true);
        vehicle.setSellType(SellType.RFD);
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public VehicleEntity createAndSaveRentableVehicleWithStatusOrderable() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setBrand("Audi");
        vehicle.setModel("RS6");
        vehicle.setEngineCapacity(5000);
        vehicle.setColour("Cosmo black");
        vehicle.setHp(840);
        vehicle.setGearType(GearType.AUTOMATIC);
        vehicle.setRegisterYear(LocalDate.now());
        vehicle.setFuelType("BENZ");
        vehicle.setPrice(BigDecimal.valueOf(150000));
        vehicle.setPriceDscnt(3);
        vehicle.setAccessories("Full-optional");
        vehicle.setRentable(true);
        vehicle.setSellType(SellType.ORDERABLE);
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public UserEntity createAndSaveSeller() {
        UserEntity seller = new SellerEntity();
        seller.setName("Luca");
        seller.setSurname("Bianchi");
        seller.setEmail("lucabianchi@gmail.com");
        seller.setPsw("LucaBianchiPsw");
        seller.setType(UserType.SELLER);
        userRepository.save(seller);
        return seller;
    }

    public UserEntity createAndSaveClient() {
        UserEntity client = new ClientEntity();
        client.setName("Marco");
        client.setSurname("Rossi");
        client.setEmail("marcorossi@gmail.com");
        client.setPsw("MarcoRossiPsw");
        client.setType(UserType.CLIENT);
        userRepository.save(client);
        return client;
    }

    public RentEntity createAndSaveRent(SellerEntity idSeller, ClientEntity idClient, VehicleEntity idVehicle) {
        RentEntity rent = new RentEntity();
        rent.setSellerId(idSeller);
        rent.setClientId(idClient);
        rent.setVehicleId(idVehicle);
        rent.setStartingDate(LocalDateTime.now());
        rent.setEndingDate(LocalDateTime.from(rent.getStartingDate()).plusDays(5));
        rent.setDailyFee(BigDecimal.valueOf(100));
        rent.setTotalFee(BigDecimal.valueOf(500));
        rent.setIsPaid(true);
        rent.setRentStatus(RentStatus.INPROGRESS);
        rentRepository.save(rent);
        return rent;
    }


    @Test
    void getVehicleByIdTestExistingVehicle() {

        VehicleEntity vehicle = createAndSaveOrderableVehicle();

        ResponseEntity<GetVehicleByIdFromSellerResponse> response = sellerService.getVehicleById(vehicle.getId());

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getMessage()).isEqualTo("Vehicle with id: " + vehicle.getId() + " correctly found. These are vehicle's data: ");
        assertThat(response.getBody().getVehicleEntity().getId()).isNotNull();
        assertThat(response.getBody().getVehicleEntity().getId()).isEqualTo(1);

    }

    @Test
    void getVehicleByIdTestNonExistingVehicle() {

        VehicleEntity vehicle = createAndSaveOrderableVehicle();

        ResponseEntity<GetVehicleByIdFromSellerResponse> response = sellerService.getVehicleById(2);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("Vehicle with id: " + 2 + " does not exists.");
    }

    @Test
    void getClientByIdExistingClient() {
        UserEntity client = createAndSaveClient();

        ResponseEntity<GetClientByIdFromSellerResponse> response = sellerService.getClientById(client.getId());

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getMessage()).isEqualTo("Client with id: " + client.getId() + " correctly found. These are client's data: ");
    }

    @Test
    void getClientByIdTestNonExistingClient() {

        UserEntity client = createAndSaveClient();

        ResponseEntity<GetClientByIdFromSellerResponse> response = sellerService.getClientById(2L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("Client with id: " + 2L + " does not exists.");
    }

    @Test
    void rentCreationOkTest() {
        SellerEntity seller = (SellerEntity) createAndSaveSeller();
        ClientEntity client = (ClientEntity) createAndSaveClient();
        VehicleEntity vehicle = createAndSaveRentableVehicle();
        RentEntity rent = createAndSaveRent(seller, client, vehicle);

        ResponseEntity<RentCreationFromSellerResponse> response = sellerService.createRent(seller, rent, client.getId(), vehicle.getId());

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getMessage()).isEqualTo("Rent correctly created");
    }

    @Test
    void rentCreationNonRentableVehicleStatusOrderableVehicleTest() {
        SellerEntity seller = (SellerEntity) createAndSaveSeller();
        ClientEntity client = (ClientEntity) createAndSaveClient();
        VehicleEntity vehicle = createAndSaveOrderableVehicle();
        RentEntity rent = createAndSaveRent(seller, client, vehicle);

        ResponseEntity<RentCreationFromSellerResponse> response = sellerService.createRent(seller, rent, client.getId(), vehicle.getId());

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("""
                Unable to create a new rent with given data.
                 Please check that:
                 -The selected vehicle exists.
                 -The selected vehicle is rentable and it's status is not 'ORDERABLE'.
                 -The client ID selected exists""");
    }

    @Test
    void rentCreationRentableVehicleStatusOrderableVehicleTest() {
        SellerEntity seller = (SellerEntity) createAndSaveSeller();
        ClientEntity client = (ClientEntity) createAndSaveClient();
        VehicleEntity vehicle = createAndSaveRentableVehicleWithStatusOrderable();
        RentEntity rent = createAndSaveRent(seller, client, vehicle);

        ResponseEntity<RentCreationFromSellerResponse> response = sellerService.createRent(seller, rent, client.getId(), vehicle.getId());

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("""
                Unable to create a new rent with given data.
                 Please check that:
                 -The selected vehicle exists.
                 -The selected vehicle is rentable and it's status is not 'ORDERABLE'.
                 -The client ID selected exists""");
    }

    @Test
    void RentCreationExistingClientTest() {
        SellerEntity seller = (SellerEntity) createAndSaveSeller();
        ClientEntity client = (ClientEntity) createAndSaveClient();
        VehicleEntity vehicle = createAndSaveRentableVehicle();
        RentEntity rent = createAndSaveRent(seller, client, vehicle);

        ResponseEntity<RentCreationFromSellerResponse> response = sellerService.createRent(seller, rent, client.getId(), vehicle.getId());

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getMessage()).isEqualTo("Rent correctly created");
    }

}
