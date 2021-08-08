package ru.komiufps.cartridges.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@ToString
@Entity
public class OutOfStockCartridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonProperty(value = "orderForConsumer")
    private OrderForConsumer orderForConsumer;

    @OneToOne
    @JsonProperty(value = "cartridgeModel")
    private CartridgeModel cartridgeModel;

}
