package ru.komiufps.cartridges.service;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.repository.CartridgeRepository;


@Service
@AllArgsConstructor
public class CartridgeService {
    private final CartridgeRepository cartridgeRepository;
    private final JdbcTemplate jdbcTemplate;

    public void addCartridge(Cartridge cartridge) {
        cartridgeRepository.save(cartridge);
    }

    public Cartridge getCartridgeBySerialNumber(String serialNumber) throws Exception {
        Long id = jdbcTemplate.queryForObject(
                "Select id from Cartridge where serial_number = ?",
                Long.class, serialNumber);


        return cartridgeRepository.findById(id).orElseThrow(
                () -> new Exception("Картриджа с таким S/N нет в базе"));

    }
}
