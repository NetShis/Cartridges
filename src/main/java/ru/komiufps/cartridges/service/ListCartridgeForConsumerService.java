package ru.komiufps.cartridges.service;


import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.ListCartridgeForConsumer;
import ru.komiufps.cartridges.repository.ListCartridgeForConsumerRepository;

@Service
@AllArgsConstructor
public class ListCartridgeForConsumerService {
    private final ListCartridgeForConsumerRepository listCartridgeForConsumerRepository;
    private final JdbcTemplate jdbcTemplate;

    public void addOrderProcessing(ListCartridgeForConsumer listCartridgeForConsumer) {
        listCartridgeForConsumerRepository.save(listCartridgeForConsumer);
    }

    public ListCartridgeForConsumer getOrderForCartridge(Cartridge cartridge) {
        Long id = jdbcTemplate.queryForObject(
                "Select id from list_cartridge_for_consumer where cartridge_id = ? and date_the_cartridge_was_return is null"
                , Long.class, cartridge.getId());
        return listCartridgeForConsumerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Не удалось найти ордер для данного картрижда")
        );
    }

}
