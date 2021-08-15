package ru.komiufps.cartridges.utils;

import lombok.Data;
import ru.komiufps.cartridges.entity.Cartridge;

@Data
public class CurrentStatusOfCartridge {
    private Cartridge cartridge;
    private StateCartridge stateCartridge;
}
