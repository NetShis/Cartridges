package ru.komiufps.cartridges.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.ListCartridgeForConsumer;
import ru.komiufps.cartridges.repository.ListCartridgeForConsumerRepository;

@Service
@AllArgsConstructor
public class ListCartridgeForConsumerService {
    private final ListCartridgeForConsumerRepository listCartridgeForConsumerRepository;

    public void addOrderProcessing(ListCartridgeForConsumer listCartridgeForConsumer) {
        listCartridgeForConsumerRepository.save(listCartridgeForConsumer);
    }

    public ListCartridgeForConsumer getOrderForCartridge(Cartridge cartridge) {
        return listCartridgeForConsumerRepository.findOrderForCartridge(cartridge);
    }

}
