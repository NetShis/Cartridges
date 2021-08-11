package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.StatusCartridgeAfterRefueller;
import ru.komiufps.cartridges.repository.StatusCartridgeAfterRefuellerRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusCartridgeAfterRefuellerService {
    private final StatusCartridgeAfterRefuellerRepository statusCartridgeAfterRefuellerRepository;

    public List<StatusCartridgeAfterRefueller> getAllStatusAfterRefueller() {
        return statusCartridgeAfterRefuellerRepository.findAll();
    }
}
