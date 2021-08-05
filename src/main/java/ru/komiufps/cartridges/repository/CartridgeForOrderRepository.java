package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForOrder;

import java.util.Optional;

public interface CartridgeForOrderRepository extends JpaRepository<CartridgeForOrder, Long> {

    @Query("FROM CartridgeForOrder as l WHERE l.cartridge = ?1 and l.dateTheCartridgeWasReturn is null")
    Optional<CartridgeForOrder> findOrderForCartridge(Cartridge cartridge);
}
