package ru.komiufps.cartridges.utils;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForConsumer;
import ru.komiufps.cartridges.entity.CartridgeForRefueller;
import ru.komiufps.cartridges.service.CartridgeForConsumerService;
import ru.komiufps.cartridges.service.CartridgeForRefuellerService;

import java.util.Optional;

@Data
@Service
public class CartridgeChecker {
    private final CartridgeForConsumerService cartridgeForConsumerService;
    private final CartridgeForRefuellerService cartridgeForRefuellerService;

    public void check(Cartridge cartridge, String operation) throws CheckerException {
        StateCartridge stateCartridge = getStateCartridge(cartridge);

        switch (stateCartridge) {
            case FullInStock:
                if (operation.equals("CartridgeAfterConsumer")
                        || operation.equals("CartridgesFromRefueller")
                        || operation.equals("CartridgesToRefueller"))
                    throw new CheckerException
                            ("Картриджа с S/N: " + cartridge.getSerialNumber() + " числится на складе заправленным!");
                break;

            case EmptyInStock:
                if (operation.equals("GiveOutCartridge")
                        || operation.equals("CartridgeAfterConsumer")
                        || operation.equals("CartridgesFromRefueller"))
                    throw new CheckerException
                            ("Картриджа с S/N: " + cartridge.getSerialNumber() + " числится на складе пустым!");
                break;
            case RefillCartridge:
                if (operation.equals("GiveOutCartridge")
                        || operation.equals("CartridgeAfterConsumer")
                        || operation.equals("CartridgesToRefueller")
                        || operation.equals("DeregisterCartridge"))
                    throw new CheckerException
                            ("Картриджа с S/N: " + cartridge.getSerialNumber() + " числится у заправщика на заправке!");
                break;
            case IssueToConsumer:
                if (operation.equals("GiveOutCartridge")
                        || operation.equals("CartridgesFromRefueller")
                        || operation.equals("CartridgesToRefueller")
                        || operation.equals("DeregisterCartridge"))
                    throw new CheckerException
                            ("Картриджа с S/N: " + cartridge.getSerialNumber() + " числится выданным пользователю!");
                break;

            case Liquidate:
                if (operation.equals("GiveOutCartridge")
                        || operation.equals("CartridgesToRefueller")
                        || operation.equals("DeregisterCartridge"))
                    throw new CheckerException
                            ("Картриджа с S/N: " + cartridge.getSerialNumber() + " числится списанным!");
                break;

            case NotDefine:
                if (operation.equals("CartridgeAfterConsumer"))
                    throw new CheckerException
                            ("Картриджа с S/N: " + cartridge.getSerialNumber() + " не числится выданным пользователю!");

                if (operation.equals("CartridgesFromRefueller"))
                    throw new CheckerException
                            ("Картриджа с S/N: " + cartridge.getSerialNumber() + " не числится отправленным на заправку!");
                break;
        }


    }

    public StateCartridge getStateCartridge(Cartridge cartridge) {
        StateCartridge stateCartridge = StateCartridge.NotDefine;
        if (cartridge.getDeregistrationDate() != null) {
            stateCartridge = StateCartridge.Liquidate;
        } else {
            Optional<CartridgeForConsumer> cartridgeForConsumer = cartridgeForConsumerService.getLastStateCartridgeForConsumer(cartridge);
            Optional<CartridgeForRefueller> cartridgeForRefueller = cartridgeForRefuellerService.getLastStateCartridgeForRefueller(cartridge);

            if (!cartridgeForConsumer.isEmpty() && !cartridgeForRefueller.isEmpty()) {
                if (cartridgeForConsumer.get().getDateTheCartridgeWasReturn() != null
                        && cartridgeForRefueller.get().getDateTheCartridgeWasReturn() != null) {
                    if (cartridgeForConsumer.get()
                            .getDateTheCartridgeWasReturn()
                            .isAfter(cartridgeForRefueller.get().getDateTheCartridgeWasReturn()))
                        stateCartridge = StateCartridge.EmptyInStock;
                    else
                        stateCartridge = StateCartridge.FullInStock;
                }
            } else {
                if (cartridgeForConsumer.isEmpty() && !cartridgeForRefueller.isEmpty())
                    stateCartridge = StateCartridge.FullInStock;
                if (cartridgeForRefueller.isEmpty() && !cartridgeForConsumer.isEmpty())
                    stateCartridge = StateCartridge.EmptyInStock;
            }

            if (!cartridgeForConsumer.isEmpty())
                if (cartridgeForConsumer.get().getDateTheCartridgeWasReturn() == null)
                    stateCartridge = StateCartridge.IssueToConsumer;

            if (!cartridgeForRefueller.isEmpty())
                if (cartridgeForRefueller.get().getDateTheCartridgeWasReturn() == null)
                    stateCartridge = StateCartridge.RefillCartridge;
        }


        return stateCartridge;
    }
}