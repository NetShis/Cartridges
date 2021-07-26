package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.komiufps.cartridges.entity.Cartridge;

public interface CartridgeRepository extends JpaRepository<Cartridge, Long> {
}


