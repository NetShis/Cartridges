package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.komiufps.cartridges.entity.OrderForCartridge;

public interface OrderForCartridgeRepository extends JpaRepository<OrderForCartridge,Long> {
}
