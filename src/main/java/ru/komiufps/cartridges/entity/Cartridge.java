package ru.komiufps.cartridges.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@Entity
public class Cartridge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne()
    @JsonProperty(value = "cartridgeModel")
    private СartridgeModel cartridgeModel;

    @JsonProperty(value = "serialNumber")
    private String serialNumber;

    @JsonProperty(value = "isDecommissioned")
    private boolean isDecommissioned;

    @JsonProperty(value = "registrationDate")
    private LocalDate registrationDate;

    public Cartridge() {
        this.isDecommissioned = false;
        this.registrationDate = LocalDate.now();
    }
}
