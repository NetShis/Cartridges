package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForRefueller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public interface CartridgeForRefuellerRepository extends JpaRepository<CartridgeForRefueller, Long> {

    @Query("FROM CartridgeForRefueller as c WHERE c.cartridge = ?1 and c.dateTheCartridgeWasReturn is null ")
    Optional<CartridgeForRefueller> theCartridgeWasReturnIsNull(Cartridge cartridge);

    @Query("From CartridgeForRefueller as cfr WHERE cfr.dateTheCartridgeWasReturn between ?1 and ?2")
    ArrayList<CartridgeForRefueller> findAllByDateRefilledCartridges(LocalDateTime start, LocalDateTime end);
}

