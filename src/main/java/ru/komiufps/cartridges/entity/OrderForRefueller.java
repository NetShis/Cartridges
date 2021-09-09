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
@Table(indexes = @Index(columnList = "orderDate"))
public class OrderForRefueller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonProperty(value = "refueller")
    private Refueller refueller;

    @JsonProperty(value = "orderDate")
    private LocalDateTime orderDate;

    public OrderForRefueller() {
        orderDate = LocalDateTime.now();
    }
}
