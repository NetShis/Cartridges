package ru.komiufps.cartridges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForConsumer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CartridgeForConsumerRepository extends JpaRepository<CartridgeForConsumer, Long> {

    @Query("FROM CartridgeForConsumer as c WHERE c.cartridge = ?1 and c.dateTheCartridgeWasReturn is null ")
    Optional<CartridgeForConsumer> theCartridgeWasReturnIsNull(Cartridge cartridge);

    @Query("from CartridgeForConsumer as cfc join OrderForConsumer as ofc on ofc.id = cfc.orderForConsumer.id where ofc.orderDate between ?1 and ?2")
    ArrayList<CartridgeForConsumer> findAllByDateIssuedCartridges(LocalDateTime start, LocalDateTime end);

    @Query("FROM CartridgeForConsumer as cfc join StatusCartridgeAfterConsumer as scac on scac.id = cfc.statusCartridgeAfterConsumer.id and scac.isGoodStatus = false and cfc.dateTheCartridgeWasReturn between ?1 and ?2")
    List<CartridgeForConsumer> findAllByDateDefectiveCartridges(LocalDateTime start, LocalDateTime end);
}

