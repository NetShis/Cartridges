package ru.komiufps.cartridges.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(indexes = @Index(columnList = "cartridge_id, dateTheCartridgeWasReturn"))
public class CartridgeForConsumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonProperty(value = "orderForConsumer")
    private OrderForConsumer orderForConsumer;

    @ManyToOne
    @JsonProperty(value = "cartridge")
    private Cartridge cartridge;

    @JsonProperty(value = "dateTheCartridgeWasReturn")
    private LocalDateTime dateTheCartridgeWasReturn;

    @ManyToOne
    @JsonProperty(value = "statusCartridgeAfterConsumer")
    private StatusCartridgeAfterConsumer statusCartridgeAfterConsumer;
}
