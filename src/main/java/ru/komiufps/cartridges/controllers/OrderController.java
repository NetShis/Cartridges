package ru.komiufps.cartridges.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.OrderForConsumer;
import ru.komiufps.cartridges.entity.ListCartridgeForConsumer;
import ru.komiufps.cartridges.service.CartridgeService;
import ru.komiufps.cartridges.service.OrderForConsumerService;
import ru.komiufps.cartridges.service.ListCartridgeForConsumerService;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderForConsumerService orderForConsumerService;
    private final CartridgeService cartridgeService;
    private final ListCartridgeForConsumerService listCartridgeForConsumerService;

    @PostMapping("/processing")
    public void orderProcessing(@RequestBody ListCartridgeForConsumer listCartridgeForConsumer) {
        listCartridgeForConsumerService.addOrderProcessing(listCartridgeForConsumer);
    }

    @GetMapping("/getOrder")
    public ListCartridgeForConsumer getOrder(@RequestParam(value = "serialNumber") String serialNumber) {
        Cartridge cartridge = cartridgeService.getCartridgeBySerialNumber(serialNumber);
        return listCartridgeForConsumerService.getOrderForCartridge(cartridge);
    }

}
