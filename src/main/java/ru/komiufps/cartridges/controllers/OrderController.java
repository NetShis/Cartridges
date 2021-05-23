package ru.komiufps.cartridges.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.komiufps.cartridges.entity.OrderForConsumer;
import ru.komiufps.cartridges.entity.ListCartridgeForConsumer;
import ru.komiufps.cartridges.service.OrderForConsumerService;
import ru.komiufps.cartridges.service.ListCartridgeForConsumerService;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderForConsumerService orderForConsumerService;
    private final ListCartridgeForConsumerService listCartridgeForConsumerService;

    @PostMapping("/create")
    public void createOrder(@RequestBody OrderForConsumer orderForConsumer) {
        orderForConsumerService.addOrderForConsumer(orderForConsumer);
    }

    @PostMapping("/processing")
    public void orderProcessing(@RequestBody ListCartridgeForConsumer listCartridgeForConsumer) {
        listCartridgeForConsumerService.addOrderProcessing(listCartridgeForConsumer);
    }

}
