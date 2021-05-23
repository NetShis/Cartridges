package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.СartridgeStatus;
import ru.komiufps.cartridges.repository.СartridgeStatusRepository;

@Service
@AllArgsConstructor
public class СartridgeStatusService {
    private final СartridgeStatusRepository cartridgeStatusRepository;

    public void addСartridgeStatus (СartridgeStatus cartridgeStatus) {
        cartridgeStatusRepository.save(cartridgeStatus);
    }

}
