package ru.komiufps.cartridges.models;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
public class Cartridges{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private СartridgeModel Name;
    private String serialNumber;
    private boolean isDecommissioned;
    private LocalDate registrationDate;

}
