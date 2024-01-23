package com.develhope.spring.entities.vehicle;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Truck")
@NoArgsConstructor
@AllArgsConstructor
public class TruckEntity extends VehicleEntity{
}