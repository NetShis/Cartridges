package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForRefueller;

import java.util.Optional;

public interface CartridgeForRefuellerRepository extends JpaRepository<CartridgeForRefueller, Long> {

    @Query("FROM CartridgeForRefueller as c WHERE c.dateTheCartridgeWasReturn = (SELECT max(cfc.dateTheCartridgeWasReturn) FROM CartridgeForRefueller as cfc where cfc.cartridge= ?1)")
    Optional<CartridgeForRefueller> maxDateTheCartridgeWasReturn (Cartridge cartridge);

    @Query("FROM CartridgeForRefueller as c WHERE c.cartridge = ?1 and c.dateTheCartridgeWasReturn is null ")
    Optional<CartridgeForRefueller> theCartridgeWasReturnIsNull (Cartridge cartridge);


}

