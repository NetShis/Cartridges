package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForRefueller;

import java.util.Optional;

public interface CartridgeForRefuellerRepository extends JpaRepository<CartridgeForRefueller, Long> {
    @Query("FROM CartridgeForRefueller as l WHERE l.cartridge = ?1 and l.dateTheCartridgeWasReturn is null")
    Optional<CartridgeForRefueller> findCartridgeForRefueller(Cartridge cartridge);
}

