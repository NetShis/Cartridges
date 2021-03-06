package ru.komiufps.cartridges.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeModel;
import ru.komiufps.cartridges.service.CartridgeModelService;
import ru.komiufps.cartridges.service.CartridgeService;
import ru.komiufps.cartridges.utils.*;

import java.time.LocalDate;
import java.util.List;


@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/cartridge")

public class CartridgeController {
    private final CartridgeService cartridgeService;
    private final CartridgeModelService cartridgeModelService;
    private final CartridgeChecker cartridgeChecker;

    @GetMapping("/getAllModels")
    public List<CartridgeModel> getAllModels() {
        return cartridgeModelService.getAllModels();
    }

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


    @GetMapping("/getCartridgeBySerialNumber")
    public Cartridge getCartridgeBySerialNumber(@RequestParam(value = "operation") String operation,
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

        cartridgeList.forEach(cartridge -> {
            cartridge.setDeregistrationDate(LocalDate.now());
            cartridge.setStateCartridge(StateCartridge.Liquidate);
            cartridgeService.save(cartridge);
        });

        return BaseResponse.builder()
                .message("Картриджы успешно выведены из эксплуатации.")
                .build();
    }


    @PostMapping("/addCartridges")
    public BaseResponse addCartridges(@RequestBody ListOfSerialNumbersToAdd listOfSerialNumbersToAdd) {

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


    @GetMapping("/getStatus")
    public CartridgeStateWidthDescriptionInRussian getStatus(@RequestParam(value = "serialNumber") String serialNumber) {
        Cartridge cartridge = cartridgeService.getCartridgeBySerialNumber(serialNumber);
        return new CartridgeStateWidthDescriptionInRussian(cartridge);
    }

}
