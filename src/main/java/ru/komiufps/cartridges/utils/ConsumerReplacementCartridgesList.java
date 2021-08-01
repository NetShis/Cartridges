package ru.komiufps.cartridges.utils;

import lombok.Data;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.Consumer;

import java.util.List;

@Data
public class ConsumerReplacementCartridgesList {
    private Consumer consumer;
    private List<Cartridge> cartridges;

}
