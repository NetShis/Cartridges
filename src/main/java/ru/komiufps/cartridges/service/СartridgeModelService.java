package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.СartridgeModel;
import ru.komiufps.cartridges.repository.СartridgeModelRepository;

@Service
@AllArgsConstructor
public class СartridgeModelService {

    private final СartridgeModelRepository cartridgeModelRepository;

    public void addСartridgeModel (СartridgeModel cartridgeModel) {
        cartridgeModelRepository.save(cartridgeModel);
    }

    public СartridgeModel getСartridgeModel (Long id) {
        return cartridgeModelRepository.getOne(id);
    }

}
