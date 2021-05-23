package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.komiufps.cartridges.entity.OutOfStockCartridge;

public interface OutOfStockCartridgeRepository extends JpaRepository<OutOfStockCartridge, Long> {
}
