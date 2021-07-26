package ru.komiufps.cartridges.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class OrderForConsumer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JsonProperty(value = "consumer")
    private Consumer consumer;

    @JsonProperty(value = "orderDate")
    private LocalDateTime orderDate;

    public OrderForConsumer() {
        orderDate = LocalDateTime.now();
    }
}
