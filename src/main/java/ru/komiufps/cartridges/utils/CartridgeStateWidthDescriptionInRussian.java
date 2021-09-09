package ru.komiufps.cartridges.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.komiufps.cartridges.entity.Cartridge;

public class CartridgeStateWidthDescriptionInRussian {
    @JsonProperty(value = "cartridge")
    private Cartridge cartridge;

    @JsonProperty(value = "stateDescriptionInRussian")
    private String stateDescriptionInRussian;

    public CartridgeStateWidthDescriptionInRussian(Cartridge cartridge) {
        this.cartridge = cartridge;
        stateDescriptionInRussian = cartridge.getStateCartridge().getDescriptionInRussian();
    }
}
