package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.ListCartridgeForConsumer;

public interface ListCartridgeForConsumerRepository extends JpaRepository<ListCartridgeForConsumer, Long> {

    @Query("FROM ListCartridgeForConsumer as l WHERE l.cartridge = ?1 and l.dateTheCartridgeWasReturn is null")
    ListCartridgeForConsumer findOrderForCartridge (Cartridge cartridge);
}
