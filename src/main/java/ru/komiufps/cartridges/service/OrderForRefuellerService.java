package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.OrderForRefueller;
import ru.komiufps.cartridges.repository.OrderForRefuellerRepository;

@Service
@AllArgsConstructor
public class OrderForRefuellerService {
    private final OrderForRefuellerRepository orderForRefuellerRepository;

    public void save(OrderForRefueller orderForRefueller) {
        orderForRefuellerRepository.save(orderForRefueller);
    }
}
