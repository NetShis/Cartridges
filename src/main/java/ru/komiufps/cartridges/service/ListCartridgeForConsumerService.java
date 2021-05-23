package ru.komiufps.cartridges.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.ListCartridgeForConsumer;
import ru.komiufps.cartridges.repository.ListCartridgeForConsumerRepository;

@Service
@AllArgsConstructor
public class ListCartridgeForConsumerService {
    private final ListCartridgeForConsumerRepository listCartridgeForConsumerRepository;

    public void addOrderProcessing (ListCartridgeForConsumer listCartridgeForConsumer) {
        listCartridgeForConsumerRepository.save(listCartridgeForConsumer);
    }
}
