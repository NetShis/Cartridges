package ru.komiufps.cartridges.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
public class CartridgeForOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonProperty(value = "orderForConsumer")
    private OrderForConsumer orderForConsumer;

    @OneToOne
    @JsonProperty(value = "cartridge")
    private Cartridge cartridge;

    @JsonProperty(value = "dateTheCartridgeWasReturn")
    private LocalDateTime dateTheCartridgeWasReturn;

    @OneToOne
    @JsonProperty(value = "cartridgeStatus")
    private СartridgeStatus cartridgeStatus;
}
