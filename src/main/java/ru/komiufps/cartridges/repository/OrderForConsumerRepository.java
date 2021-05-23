package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.komiufps.cartridges.entity.OrderForConsumer;

public interface OrderForConsumerRepository extends JpaRepository<OrderForConsumer, Long> {
}
