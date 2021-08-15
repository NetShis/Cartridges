package ru.komiufps.cartridges.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.komiufps.cartridges.entity.Cartridge;


import java.util.Optional;

public interface CartridgeRepository extends JpaRepository<Cartridge, Long> {
    @Query("FROM Cartridge as c WHERE c.serialNumber = ?1")
    Optional<Cartridge> findCartridgeSerialNumber(String serialNumber);

}


