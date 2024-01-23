package com.develhope.spring.entities.vehicle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
@Entity
@Table(name = "Vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String marca;
    @Column(nullable = false)
    private String modello;
    @Column(nullable = false)
    private int cilindrata;
    @Column(nullable = false)
    private String colore;
    @Column(nullable = false)
    private int potenza;
    @Column(nullable = false)
    private String tipoCambio;
    @Column(nullable = false)
    private OffsetDateTime annoImmatricolazione;
    @Column(nullable = false)
    private String alimentazione;
    @Column(nullable = false)
    private BigDecimal prezzo;
    @Column(nullable = false)
    private int scontoPrezzo;
    @Column(nullable = false)
    private String accessori;

}
