package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.CartridgeModel;
import ru.komiufps.cartridges.repository.СartridgeModelRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CartridgeModelService {

    private final СartridgeModelRepository cartridgeModelRepository;

    public void save(CartridgeModel cartridgeModel) {
        cartridgeModelRepository.save(cartridgeModel);
    }

    public CartridgeModel getCartridgeModel(Long id) {
        return cartridgeModelRepository.findById(id).get();
    }

    public List<CartridgeModel> getAllModels() {
        return cartridgeModelRepository.findAll();
    }

}
