package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.komiufps.cartridges.entity.Consumer;

public interface ConsumerRepository extends JpaRepository<Consumer,Long> {
}
