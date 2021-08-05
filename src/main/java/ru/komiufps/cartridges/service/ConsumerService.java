package ru.komiufps.cartridges.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.Consumer;
import ru.komiufps.cartridges.repository.ConsumerRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsumerService {

    private final ConsumerRepository consumerRepository;

    public void addConsumer(Consumer consumer) {
        consumerRepository.save(consumer);
    }

    public List<Consumer> getAllConsumers() {
        return consumerRepository.findAll();
    }
}
