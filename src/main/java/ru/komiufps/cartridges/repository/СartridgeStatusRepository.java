package ru.komiufps.cartridges.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.komiufps.cartridges.entity.СartridgeStatus;

public interface СartridgeStatusRepository extends JpaRepository<СartridgeStatus,Long> {
}
