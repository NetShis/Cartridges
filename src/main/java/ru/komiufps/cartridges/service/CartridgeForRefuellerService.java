package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.CartridgeForRefueller;
import ru.komiufps.cartridges.repository.CartridgeForRefuellerRepository;

@Service
@AllArgsConstructor
public class CartridgeForRefuellerService {
    private final CartridgeForRefuellerRepository cartridgeForRefuellerRepository;

    public void save(CartridgeForRefueller cartridgeForRefueller) {
        cartridgeForRefuellerRepository.save(cartridgeForRefueller);

    }
}
