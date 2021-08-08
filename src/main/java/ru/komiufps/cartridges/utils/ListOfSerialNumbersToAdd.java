package ru.komiufps.cartridges.utils;

import lombok.Data;
import ru.komiufps.cartridges.entity.CartridgeModel;

import java.util.List;

@Data
public class ListOfSerialNumbersToAdd {
    private CartridgeModel cartridgeModel;
    private List<String> serialNumbers;

}
