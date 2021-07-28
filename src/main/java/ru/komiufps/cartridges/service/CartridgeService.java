package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.repository.CartridgeRepository;


@Service
@AllArgsConstructor
public class CartridgeService {
    private final CartridgeRepository cartridgeRepository;

    public void addCartridge(Cartridge cartridge) {
        cartridgeRepository.save(cartridge);
    }

    public Cartridge getCartridgeBySerialNumber(String serialNumber) {
        return cartridgeRepository.findCartridgeSerialNumber(serialNumber);
    }
}
