package ru.komiufps.cartridges.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeModel;
import ru.komiufps.cartridges.entity.СartridgeStatus;
import ru.komiufps.cartridges.service.CartridgeForOrderService;
import ru.komiufps.cartridges.service.CartridgeModelService;
import ru.komiufps.cartridges.service.CartridgeService;
import ru.komiufps.cartridges.service.СartridgeStatusService;
import ru.komiufps.cartridges.utils.BaseResponse;
import ru.komiufps.cartridges.utils.CartridgeChecker;
import ru.komiufps.cartridges.utils.CheckerException;
import ru.komiufps.cartridges.utils.ListOfSerialNumbersToAdd;

import java.util.List;


@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/cartridge")

public class CartridgeController {
    private final CartridgeService cartridgeService;
    private final CartridgeModelService cartridgeModelService;
    private final СartridgeStatusService cartridgeStatusService;
    private final CartridgeForOrderService cartridgeForOrderService;
    private final CartridgeChecker cartridgeChecker;

    @GetMapping("/checkCartridgeSerialNumber")
    public String checkCartridgeSerialNumber(@RequestParam(value = "serialNumber") String serialNumber) {
        try {
            cartridgeService.getCartridgeBySerialNumber(serialNumber);
        } catch (ResponseStatusException e) {
            return serialNumber;
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Картриджа с S/N: " + serialNumber + " уже зарегистрирован в базе!");
    }

    @PostMapping("/addStatus")
    public void addCartridgeStatus(@RequestBody СartridgeStatus cartridgeStatus) {
        cartridgeStatusService.addСartridgeStatus(cartridgeStatus);
    }

    @GetMapping("/getAllStatus")
    public List<СartridgeStatus> getAllStatus() {
        return cartridgeStatusService.getAllStatus();
    }

    @GetMapping("/getAllModels")
    public List<CartridgeModel> getAllModels() {
        return cartridgeModelService.getAllModels();
    }


    @GetMapping("/getCartrigeBySerialNumber")
    public Cartridge getCartrigeBySerialNumber(@RequestParam(value = "operation") String operation,
                                               @RequestParam(value = "serialNumber") String serialNumber) {

        Cartridge cartridge = cartridgeService.getCartridgeBySerialNumber(serialNumber);

        try {
            cartridgeChecker.check(cartridge, operation);
        } catch (CheckerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }

        return cartridge;
    }


    @PutMapping("/deregister")
    public BaseResponse deregisterCartridge(@RequestBody List<Cartridge> cartridgeList) {
        cartridgeService.deregisterCartridge(cartridgeList);

        return BaseResponse.builder()
                .message("Картриджы успешно выведены из эксплуатации.")
                .build();
    }

    @PostMapping("/addCartridges")
    public BaseResponse addCartridges(@RequestBody ListOfSerialNumbersToAdd listOfSerialNumbersToAdd) {
        System.out.println(listOfSerialNumbersToAdd.toString());

        listOfSerialNumbersToAdd.getSerialNumbers()
                .forEach(serialNumber -> {
                    Cartridge cartridge = new Cartridge();
                    cartridge.setCartridgeModel(listOfSerialNumbersToAdd.getCartridgeModel());
                    cartridge.setSerialNumber(serialNumber);
                    cartridgeService.save(cartridge);
                });

        return BaseResponse
                .builder()
                .message("Новые картриджы добавлены в базу!")
                .build();
    }

}
