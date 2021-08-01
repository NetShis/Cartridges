package ru.komiufps.cartridges.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForOrder;
import ru.komiufps.cartridges.entity.OrderForConsumer;
import ru.komiufps.cartridges.service.CartridgeService;
import ru.komiufps.cartridges.service.CartridgeForOrderService;
import ru.komiufps.cartridges.service.OrderForConsumerService;
import ru.komiufps.cartridges.utils.BaseResponse;
import ru.komiufps.cartridges.utils.ConsumerReplacementCartridgesList;

import java.util.ArrayList;
import java.util.List;


@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final CartridgeService cartridgeService;
    private final CartridgeForOrderService cartridgeForOrderService;
    private final OrderForConsumerService orderForConsumerService;

    @PutMapping("/closeOrders")
    public void closeAnOrders(@RequestBody List<CartridgeForOrder> cartridgesForOrder) {
        cartridgeForOrderService.closeOrders(cartridgesForOrder);
    }

    @PostMapping("/createOrder")
    public BaseResponse createOrder(@RequestBody ConsumerReplacementCartridgesList consumerReplacementCartridgesList) {
        OrderForConsumer orderForConsumer = new OrderForConsumer();
        orderForConsumer.setConsumer(consumerReplacementCartridgesList.getConsumer());

        List<Cartridge> cartridgeList = consumerReplacementCartridgesList.getCartridges();
        List<CartridgeForOrder> cartridgesForOrder = new ArrayList<>();

        cartridgeList.forEach(cartridge -> {
            CartridgeForOrder cartridgeForOrder = new CartridgeForOrder();
            cartridgeForOrder.setOrderForConsumer(orderForConsumer);
            cartridgeForOrder.setCartridge(cartridge);
            cartridgesForOrder.add(cartridgeForOrder);
        });

        orderForConsumerService.saveOrder(orderForConsumer);
        cartridgeForOrderService.createOrder(cartridgesForOrder);

        return BaseResponse.builder()
                .message("Заказ успешно обработан")
                .build();
    }

    @GetMapping("/getOrder")
    public CartridgeForOrder getOrder(@RequestParam(value = "serialNumber") String serialNumber) {
        Cartridge cartridge = cartridgeService.getCartridgeBySerialNumber(serialNumber);
        if (cartridge == null)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Картриджа с S/N: " + serialNumber + " нет в базе");

        else {
            CartridgeForOrder cartridgeForOrder = cartridgeForOrderService
                    .getOrderForCartridge(cartridge);

            if (cartridgeForOrder == null)
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Картридж S/N: " + serialNumber + " не числится выданным");
            else return cartridgeForOrder;
        }
    }
}
