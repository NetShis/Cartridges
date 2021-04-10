package ru.komiufps.cartridges.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class СartridgeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cartridgeModelName;

    public СartridgeModel(String cartridgeModelName) {
        this.cartridgeModelName = cartridgeModelName;
    }

    public СartridgeModel() {
    }
}

