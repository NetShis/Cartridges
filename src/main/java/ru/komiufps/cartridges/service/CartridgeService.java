package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.repository.CartridgeRepository;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class CartridgeService {
    private final CartridgeRepository cartridgeRepository;

    public void addCartridge(Cartridge cartridge) {
        cartridgeRepository.save(cartridge);
    }

    public Cartridge getCartridgeBySerialNumber(String serialNumber) {
        return cartridgeRepository
                .findCartridgeSerialNumber(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Картриджа с S/N: " + serialNumber + " нет в базе"));
    }

    public void deregisterCartridge(List<Cartridge> cartridgeList) {
        cartridgeList.forEach(cartridge -> {
            cartridge.setDeregistrationDate(LocalDate.now());
            cartridgeRepository.save(cartridge);
        });
    }
}
