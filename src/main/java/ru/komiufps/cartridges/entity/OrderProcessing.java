package ru.komiufps.cartridges.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class OrderProcessing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private OrderForCartridge orderForCartridge;

    @OneToOne
    @JsonProperty(value = "cartridge")
    private Cartridge cartridge;

    private Timestamp dateTheCartridgeWasTaken;

    private Timestamp dateTheCartridgeWasReturn;

    @OneToOne
    @JsonProperty(value = "cartridgeStatus")
    private СartridgeStatus cartridgeStatus;


}
