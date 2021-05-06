package ru.komiufps.cartridges.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
public class Cartridge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty(value = "name")
    private СartridgeModel name;

    @JsonProperty(value = "serialNumber")
    private String serialNumber;

    @JsonProperty(value = "isDecommissioned")
    private boolean isDecommissioned;

    @JsonProperty(value = "registrationDate")
    private LocalDate registrationDate;

    public Cartridge(СartridgeModel name, String serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.isDecommissioned = false;
        this.registrationDate = LocalDate.now();
    }

}
