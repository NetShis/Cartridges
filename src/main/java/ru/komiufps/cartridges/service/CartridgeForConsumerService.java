package ru.komiufps.cartridges.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForConsumer;
import ru.komiufps.cartridges.repository.CartridgeForConsumerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartridgeForConsumerService {
    private final CartridgeForConsumerRepository cartridgeForConsumerRepository;

    public Optional<CartridgeForConsumer> getOrderForCartridge(Cartridge cartridge) {
        return cartridgeForConsumerRepository.theCartridgeWasReturnIsNull(cartridge);
    }

    public void save(CartridgeForConsumer cartridgeForConsumer) {
        cartridgeForConsumerRepository.save(cartridgeForConsumer);
    }

    public List<CartridgeForConsumer> findAllByDateIssuedCartridges(LocalDateTime start, LocalDateTime end) {
        return cartridgeForConsumerRepository.findAllByDateIssuedCartridges(start, end);
    }

    public List<CartridgeForConsumer> findAllByDateDefectiveCartridges(LocalDateTime start, LocalDateTime end) {
        return cartridgeForConsumerRepository.findAllByDateDefectiveCartridges(start, end);
    }
}
