package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForConsumer;

import java.util.Optional;

public interface CartridgeForConsumerRepository extends JpaRepository<CartridgeForConsumer, Long> {

    @Query("FROM CartridgeForConsumer as l WHERE l.cartridge = ?1 and l.dateTheCartridgeWasReturn is null")
    Optional<CartridgeForConsumer> findOrderForCartridge(Cartridge cartridge);
}
