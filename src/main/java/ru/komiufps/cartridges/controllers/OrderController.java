package ru.komiufps.cartridges.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForOrder;
import ru.komiufps.cartridges.entity.OrderForConsumer;
import ru.komiufps.cartridges.service.CartridgeForOrderService;
import ru.komiufps.cartridges.service.CartridgeService;
import ru.komiufps.cartridges.service.OrderForConsumerService;
import ru.komiufps.cartridges.utils.BaseResponse;
import ru.komiufps.cartridges.utils.CartridgeChecker;
import ru.komiufps.cartridges.utils.CheckerException;
import ru.komiufps.cartridges.utils.ConsumerReplacementCartridgesList;

import java.util.List;


@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final CartridgeService cartridgeService;
    private final CartridgeForOrderService cartridgeForOrderService;
    private final OrderForConsumerService orderForConsumerService;
    private final CartridgeChecker cartridgeChecker;

    @PutMapping("/closeOrders")
    public BaseResponse closeOrders(@RequestBody List<CartridgeForOrder> cartridgesForOrder) {
        cartridgeForOrderService.closeOrders(cartridgesForOrder);

        return BaseResponse
                .builder()
                .message("Заказ успешно закрыт.")
                .build();
    }

    @PostMapping("/createOrder")
    public BaseResponse createOrder(@RequestBody ConsumerReplacementCartridgesList consumerReplacementCartridgesList) {
        OrderForConsumer orderForConsumer = new OrderForConsumer();
        orderForConsumer.setConsumer(consumerReplacementCartridgesList.getConsumer());

        orderForConsumerService.save(orderForConsumer);

        consumerReplacementCartridgesList.getCartridges()
                .forEach(cartridge -> {
                    CartridgeForOrder cartridgeForOrder = new CartridgeForOrder();
                    cartridgeForOrder.setOrderForConsumer(orderForConsumer);
                    cartridgeForOrder.setCartridge(cartridge);
                    cartridgeForOrderService.save(cartridgeForOrder);
                });


        return BaseResponse
                .builder()
                .message("Заказ успешно создан.")
                .build();

    }

    @GetMapping("/getOrder")
    public CartridgeForOrder getOrder(@RequestParam(value = "serialNumber") String serialNumber) {
        Cartridge cartridge = cartridgeService.getCartridgeBySerialNumber(serialNumber);
        try {
            cartridgeChecker.check(cartridge, "takeCartridge");
        } catch (CheckerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }

        return cartridgeForOrderService
                .getOrderForCartridge(cartridge)
                .get();

    }
}
