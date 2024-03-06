package com.develhope.spring;

import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.client.ClientRepository;
import com.develhope.spring.rent.*;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.seller.SellerRepository;
import com.develhope.spring.seller.SellerService;
import com.develhope.spring.seller.sellerControllerResponse.*;
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

    public RentEntity createAndSaveRent(SellerEntity idSeller, RentDtoInput rentDtoInput) {
        RentEntity rent = new RentEntity();
        rent.setSellerId(idSeller);
        rent.setClientId(clientRepository.findById(rentDtoInput.getIdClient()).get());
        rent.setVehicleId(vehicleRepository.findById(rentDtoInput.getIdVehicle()).get());
        rent.setStartingDate(LocalDateTime.now());
        rent.setEndingDate(LocalDateTime.from(rent.getStartingDate()).plusDays(5));
        rent.setDailyFee(BigDecimal.valueOf(100));
        rent.setTotalFee(BigDecimal.valueOf(500));
        rent.setIsPaid(true);
        rent.setRentStatus(RentStatus.INPROGRESS);
        rentRepository.save(rent);
        return rent;
    }

    public RentDtoInput createRentDtoInput(SellerEntity seller, ClientEntity client, VehicleEntity vehicle) {
        RentDtoInput rentDtoInput = new RentDtoInput();
        rentDtoInput.setIdSeller(seller.getId());
        rentDtoInput.setIdClient(client.getId());
        rentDtoInput.setIdVehicle(vehicle.getId());
        rentDtoInput.setStartRent(LocalDateTime.now());
        rentDtoInput.setEndRent(LocalDateTime.from(rentDtoInput.getStartRent().plusDays(5)));
        rentDtoInput.setDailyFee(BigDecimal.valueOf(50));
        rentDtoInput.setTotalFee(BigDecimal.valueOf(250));
        rentDtoInput.setIsPaid(true);
        return rentDtoInput;
    }

    public ToUpdateRentDtoInput createToUpdateRentDtoInput() {
        ToUpdateRentDtoInput toUpdateRentDtoInput = new ToUpdateRentDtoInput();
        toUpdateRentDtoInput.setRentStartingDate(LocalDateTime.now());
        toUpdateRentDtoInput.setRentEndingDate(toUpdateRentDtoInput.getRentStartingDate().plusDays(5));
        toUpdateRentDtoInput.setDailyFee(BigDecimal.valueOf(40));
        toUpdateRentDtoInput.setTotalFee(BigDecimal.valueOf(200));
        toUpdateRentDtoInput.setIsPaid(true);
        return toUpdateRentDtoInput;
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
        RentDtoInput rentDtoInput = createRentDtoInput(seller, client, vehicle);


        ResponseEntity<RentCreationFromSellerResponse> response = sellerService.createRent(seller, rentDtoInput);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getMessage()).isEqualTo("Rent correctly created");
    }

    @Test
    void rentCreationNonRentableVehicleStatusOrderableVehicleTest() {
        SellerEntity seller = (SellerEntity) createAndSaveSeller();
        ClientEntity client = (ClientEntity) createAndSaveClient();
        VehicleEntity vehicle = createAndSaveOrderableVehicle();
        RentDtoInput rentDtoInput = createRentDtoInput(seller, client, vehicle);

        ResponseEntity<RentCreationFromSellerResponse> response = sellerService.createRent(seller, rentDtoInput);

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
        RentDtoInput rentDtoInput = createRentDtoInput(seller, client, vehicle);

        ResponseEntity<RentCreationFromSellerResponse> response = sellerService.createRent(seller, rentDtoInput);

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
        RentDtoInput rentDtoInput = createRentDtoInput(seller, client, vehicle);

        ResponseEntity<RentCreationFromSellerResponse> response = sellerService.createRent(seller, rentDtoInput);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getMessage()).isEqualTo("Rent correctly created");
    }

    @Test
    void RentCreationNonExistingClientTest() {
        SellerEntity seller = (SellerEntity) createAndSaveSeller();
        ClientEntity client = (ClientEntity) createAndSaveClient();
        ClientEntity client2 = new ClientEntity();
        client2.setId(5L);
        VehicleEntity vehicle = createAndSaveRentableVehicle();
        RentDtoInput rentDtoInput = createRentDtoInput(seller, client2, vehicle);

        ResponseEntity<RentCreationFromSellerResponse> response = sellerService.createRent(seller, rentDtoInput);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("""
                Unable to create a new rent with given data.
                 Please check that:
                 -The selected vehicle exists.
                 -The selected vehicle is rentable and it's status is not 'ORDERABLE'.
                 -The client ID selected exists""");
    }

    @Test
    void UpdateExistingRent() {
        SellerEntity seller = (SellerEntity) createAndSaveSeller();
        ClientEntity client = (ClientEntity) createAndSaveClient();
        VehicleEntity vehicle = createAndSaveRentableVehicle();
        RentDtoInput rentDtoInput = createRentDtoInput(seller, client, vehicle);
        RentEntity rent = createAndSaveRent(seller, rentDtoInput);

        ToUpdateRentDtoInput toUpdateRentDtoInput = createToUpdateRentDtoInput();
        ResponseEntity<RentUpdateFromSellerResponse> response = sellerService.updateRent(toUpdateRentDtoInput, rent.getId());

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getMessage()).isEqualTo("The rent with id " + rent.getId() + " has been correctly updated.");
    }

    @Test
    void UpdateNonExistingRent() {
        SellerEntity seller = (SellerEntity) createAndSaveSeller();
        ClientEntity client = (ClientEntity) createAndSaveClient();
        VehicleEntity vehicle = createAndSaveRentableVehicle();
        RentDtoInput rentDtoInput = createRentDtoInput(seller, client, vehicle);
        RentEntity rent = createAndSaveRent(seller, rentDtoInput);
        RentEntity rent2 = new RentEntity();
        rent2.setId(5L);

        ToUpdateRentDtoInput toUpdateRentDtoInput = createToUpdateRentDtoInput();
        ResponseEntity<RentUpdateFromSellerResponse> response = sellerService.updateRent(toUpdateRentDtoInput, rent2.getId());

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("The rent with id " + rent2.getId() + " can't be updated.\n" +
                "Please check whether the selected ID correctly matches an existing rent.");
    }

    @Test
    void ExistingRentDeletion() {
        SellerEntity seller = (SellerEntity) createAndSaveSeller();
        ClientEntity client = (ClientEntity) createAndSaveClient();
        VehicleEntity vehicle = createAndSaveRentableVehicle();
        RentDtoInput rentDtoInput = createRentDtoInput(seller, client, vehicle);
        RentEntity rent = createAndSaveRent(seller, rentDtoInput);

        ResponseEntity<RentDeletionByIdFromSellerResponse> response = sellerService.deleteRent(rent.getId());

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getMessage()).isEqualTo("The rent with id " + rent.getId() + " has been deleted");
        assertThat(response.getBody().getRentEntity().getRentStatus()).isEqualTo(RentStatus.DELETED);
    }

}
