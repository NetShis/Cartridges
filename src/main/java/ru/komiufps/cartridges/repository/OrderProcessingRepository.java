package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.komiufps.cartridges.entity.OrderProcessing;

public interface OrderProcessingRepository extends JpaRepository<OrderProcessing,Long> {
}
