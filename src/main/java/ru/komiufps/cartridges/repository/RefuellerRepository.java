package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.komiufps.cartridges.entity.Refueller;

public interface RefuellerRepository extends JpaRepository<Refueller, Long> {
}
