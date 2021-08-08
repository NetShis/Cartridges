package ru.komiufps.cartridges.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@Entity
public class Cartridge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JsonProperty(value = "cartridgeModel")
    private CartridgeModel cartridgeModel;

    @Column(unique = true)
    @JsonProperty(value = "serialNumber")
    private String serialNumber;

    @JsonProperty(value = "registrationDate")
    private LocalDate registrationDate;

    @JsonProperty(value = "deregistrationDate")
    private LocalDate deregistrationDate;

    public Cartridge() {
        this.registrationDate = LocalDate.now();
    }

}
