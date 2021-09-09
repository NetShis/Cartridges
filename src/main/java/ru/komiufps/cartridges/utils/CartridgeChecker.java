package ru.komiufps.cartridges.utils;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.service.CartridgeForConsumerService;
import ru.komiufps.cartridges.service.CartridgeForRefuellerService;

@Data
@Service
public class CartridgeChecker {
    private final CartridgeForConsumerService cartridgeForConsumerService;
    private final CartridgeForRefuellerService cartridgeForRefuellerService;

    public void check(Cartridge cartridge, String operation) throws CheckerException {
        StateCartridge stateCartridge = cartridge.getStateCartridge();

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

            case DefectiveInStock:
                if (operation.equals("GiveOutCartridge")
                        || operation.equals("CartridgeAfterConsumer")
                        || operation.equals("CartridgesFromRefueller")
                        || operation.equals("CartridgesToRefueller"))
                    throw new CheckerException
                            ("Картриджа с S/N: " + cartridge.getSerialNumber() + " числится на складе дефектным!");
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
                        || operation.equals("CartridgeAfterConsumer")
                        || operation.equals("CartridgesToRefueller")
                        || operation.equals("CartridgesFromRefueller")
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
}