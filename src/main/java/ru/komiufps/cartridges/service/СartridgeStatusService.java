package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.СartridgeStatus;
import ru.komiufps.cartridges.repository.СartridgeStatusRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class СartridgeStatusService {
    private final СartridgeStatusRepository cartridgeStatusRepository;


    public void addСartridgeStatus (СartridgeStatus cartridgeStatus) {
        cartridgeStatusRepository.save(cartridgeStatus);
    }

    public List<СartridgeStatus> getAllStatus() {
        return cartridgeStatusRepository.findAll();
    }


}
