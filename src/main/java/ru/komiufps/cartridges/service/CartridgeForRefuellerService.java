package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForRefueller;
import ru.komiufps.cartridges.repository.CartridgeForRefuellerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartridgeForRefuellerService {
    private final CartridgeForRefuellerRepository cartridgeForRefuellerRepository;

    public void save(CartridgeForRefueller cartridgeForRefueller) {
        cartridgeForRefuellerRepository.save(cartridgeForRefueller);

    }

    public Optional<CartridgeForRefueller> getCartridgeForRefueller(Cartridge cartridge) {
        return cartridgeForRefuellerRepository.findCartridgeForRefueller(cartridge);
    }

    public void closeOrders(List<CartridgeForRefueller> cartridgeForRefuellerList) {
        cartridgeForRefuellerList.forEach(cartridgeForRefuell -> {
            cartridgeForRefuell.setDateTheCartridgeWasReturn(LocalDateTime.now());
            cartridgeForRefuellerRepository.save(cartridgeForRefuell);
        });
    }
}

