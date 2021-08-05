package ru.komiufps.cartridges.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForOrder;
import ru.komiufps.cartridges.repository.CartridgeForOrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartridgeForOrderService {
    private final CartridgeForOrderRepository cartridgeForOrderRepository;

    public void closeOrders(List<CartridgeForOrder> cartridgesForOrder) {
        cartridgesForOrder.forEach(item -> {
            item.setDateTheCartridgeWasReturn(LocalDateTime.now());
            cartridgeForOrderRepository.save(item);
        });
    }

    public Optional<CartridgeForOrder> getOrderForCartridge(Cartridge cartridge) {
        return cartridgeForOrderRepository.findOrderForCartridge(cartridge);
    }

    public void createOrder (List <CartridgeForOrder> cartridgesForOrder ) {
        cartridgeForOrderRepository.saveAll(cartridgesForOrder);
    }

}
