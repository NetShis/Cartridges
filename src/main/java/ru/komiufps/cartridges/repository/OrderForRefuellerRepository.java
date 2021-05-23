package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.komiufps.cartridges.entity.OrderForRefueller;

public interface OrderForRefuellerRepository extends JpaRepository<OrderForRefueller, Long> {
}
