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
public class CartridgeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(value = "requiredLimitInStock")
    private int requiredLimitInStock;

    @JsonProperty(value = "cartridgeModel")
    @Column(unique = true)
    private String cartridgeModel;

}

