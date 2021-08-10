package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.komiufps.cartridges.entity.CartridgeForRefueller;

public interface CartridgeForRefuellerRepository extends JpaRepository<CartridgeForRefueller, Long> {
}
