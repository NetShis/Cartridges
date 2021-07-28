package ru.komiufps.cartridges.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.ListCartridgeForConsumer;
import ru.komiufps.cartridges.service.CartridgeService;
import ru.komiufps.cartridges.service.ListCartridgeForConsumerService;


@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final CartridgeService cartridgeService;
    private final ListCartridgeForConsumerService listCartridgeForConsumerService;

    @PostMapping("/processing")
    public void orderProcessing(@RequestBody ListCartridgeForConsumer listCartridgeForConsumer) {
        listCartridgeForConsumerService.addOrderProcessing(listCartridgeForConsumer);
    }

    @GetMapping("/getOrder")
    public ListCartridgeForConsumer getOrder(@RequestParam(value = "serialNumber") String serialNumber) {
        Cartridge cartridge = cartridgeService.getCartridgeBySerialNumber(serialNumber);
        if (cartridge == null)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Картриджа с S/N: " + serialNumber + " нет в базе");

        else {
            ListCartridgeForConsumer listCartridgeForConsumer = listCartridgeForConsumerService.getOrderForCartridge(cartridge);
            if (listCartridgeForConsumer == null)
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Картридж S/N: " + serialNumber + " не числится выданным");
            else return listCartridgeForConsumer;
        }
    }
}
