package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.StatusCartridgeAfterConsumer;
import ru.komiufps.cartridges.repository.StatusCartridgeAfterConsumerRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusCartridgeAfterConsumerService {
    private final StatusCartridgeAfterConsumerRepository statusCartridgeAfterConsumerRepository;

    public List<StatusCartridgeAfterConsumer> getAllStatusAfterConsumer() {
        return statusCartridgeAfterConsumerRepository.findAll();
    }

}
