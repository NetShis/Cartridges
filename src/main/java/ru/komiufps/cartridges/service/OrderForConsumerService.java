package ru.komiufps.cartridges.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.OrderForConsumer;
import ru.komiufps.cartridges.repository.OrderForConsumerRepository;

@Service
@AllArgsConstructor
public class OrderForConsumerService {
    private final OrderForConsumerRepository orderForConsumerRepository;

    public void save(OrderForConsumer orderForConsumer) {
        orderForConsumerRepository.save(orderForConsumer);
    }
}
