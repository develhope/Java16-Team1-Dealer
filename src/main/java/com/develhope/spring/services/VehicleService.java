package com.develhope.spring.services;

import com.develhope.spring.entities.vehicle.*;
import com.develhope.spring.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

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




    public VehicleEntity newVehicle(VehicleEntity vehicle, String type) {
        switch (type.toLowerCase()) {
            case "truck":
                return vehicleRepository.save(createTruck(vehicle));
            case "moto":
                return vehicleRepository.save(createMoto(vehicle));
            case "auto":
                return vehicleRepository.save(createAuto(vehicle));
            case "scooter":
                return vehicleRepository.save(createScooter(vehicle));
            default:
                return null;
        }

    }

}
