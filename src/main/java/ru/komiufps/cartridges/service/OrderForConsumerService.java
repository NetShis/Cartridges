package ru.komiufps.cartridges.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.OrderForConsumer;
import ru.komiufps.cartridges.repository.OrderForConsumerRepository;

@Service
@AllArgsConstructor
public class OrderForConsumerService {
    private final OrderForConsumerRepository orderForConsumerRepository;

    public void addOrderForConsumer (OrderForConsumer orderForConsumer) {
        orderForConsumerRepository.save(orderForConsumer);
    }

    public void saveOrder (OrderForConsumer orderForConsumer) {
        orderForConsumerRepository.save(orderForConsumer);
    }
}
