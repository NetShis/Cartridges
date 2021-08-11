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
        if (operation.equals("GiveOutCartridge")) {
            deregistrationDateCheck(cartridge);
            cartridgeGiveOutCheck(cartridge);
            cartridgeAtRefuelingCheck(cartridge);
        }

        if (operation.equals("CartridgeAfterConsumer")) {
            cartridgeNotGiveOutCheck(cartridge);
            deregistrationDateCheck(cartridge);
            cartridgeAtRefuelingCheck(cartridge);
        }

        if (operation.equals("CartridgesFromRefueller")) {
            cartridgeGiveOutCheck(cartridge);
            cartridgeNotAtRefuelingCheck(cartridge);
            deregistrationDateCheck(cartridge);
        }

        if (operation.equals("CartridgesToRefueller")) {
            cartridgeGiveOutCheck(cartridge);
            cartridgeAtRefuelingCheck(cartridge);
            deregistrationDateCheck(cartridge);
        }

        if (operation.equals("DeregisterCartridge")) {
            deregistrationDateCheck(cartridge);
            cartridgeGiveOutCheck(cartridge);
            cartridgeAtRefuelingCheck(cartridge);
        }

    }

    private void deregistrationDateCheck(Cartridge cartridge) throws CheckerException {
        if (cartridge.getDeregistrationDate() != null)
            throw new CheckerException
                    ("Картриджа с S/N: " + cartridge.getSerialNumber() + " снять с регистрации.");
    }

    private void cartridgeGiveOutCheck(Cartridge cartridge) throws CheckerException {
        if (!cartridgeForConsumerService.getOrderForCartridge(cartridge).isEmpty())
            throw new CheckerException
                    ("Картриджа с S/N: " + cartridge.getSerialNumber() + " числится выданным.");
    }

    private void cartridgeNotGiveOutCheck(Cartridge cartridge) throws CheckerException {
        if (cartridgeForConsumerService.getOrderForCartridge(cartridge).isEmpty())
            throw new CheckerException
                    ("Картриджа с S/N: " + cartridge.getSerialNumber() + " не числится выданным.");
    }

    private void cartridgeAtRefuelingCheck(Cartridge cartridge) throws CheckerException {
        if (!cartridgeForRefuellerService.getCartridgeForRefueller(cartridge).isEmpty())
            throw new CheckerException
                    ("Картриджа с S/N: " + cartridge.getSerialNumber() + " числиться у заправщика на заправке.");
    }

    private void cartridgeNotAtRefuelingCheck(Cartridge cartridge) throws CheckerException {
        if (cartridgeForRefuellerService.getCartridgeForRefueller(cartridge).isEmpty())
            throw new CheckerException
                    ("Картриджа с S/N: " + cartridge.getSerialNumber() + " не числиться у заправщика на заправке.");
    }
}
