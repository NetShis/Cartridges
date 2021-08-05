package ru.komiufps.cartridges.utils;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.service.CartridgeForOrderService;

@Data
@Service
public class CartridgeChecker {
    private final CartridgeForOrderService cartridgeForOrderService;

    public void check(Cartridge cartridge, String operation) throws CheckerException {
        if (operation.equals("giveOutCartridge")) {
            deregistrationDateCheck(cartridge);
            cartridgeGiveOutCheck(cartridge);
        }

        if (operation.equals("deregisterCartridge")) {
            deregistrationDateCheck(cartridge);
            cartridgeGiveOutCheck(cartridge);
        }

        if (operation.equals("takeCartridge")) {
            cartridgeNotGiveOutCheck(cartridge);
            deregistrationDateCheck(cartridge);
        }

    }

    private void deregistrationDateCheck(Cartridge cartridge) throws CheckerException {
        if (cartridge.getDeregistrationDate() != null)
            throw new CheckerException
                    ("Картриджа с S/N: " + cartridge.getSerialNumber() + " снять с регистрации");
    }

    private void cartridgeGiveOutCheck(Cartridge cartridge) throws CheckerException {
        if (!cartridgeForOrderService.getOrderForCartridge(cartridge).isEmpty())
            throw new CheckerException
                    ("Картриджа с S/N: " + cartridge.getSerialNumber() + " числится выданным");
    }

    private void cartridgeNotGiveOutCheck(Cartridge cartridge) throws CheckerException {
        if (cartridgeForOrderService.getOrderForCartridge(cartridge).isEmpty())
            throw new CheckerException
                    ("Картриджа с S/N: " + cartridge.getSerialNumber() + " не числится выданным");
    }
}
