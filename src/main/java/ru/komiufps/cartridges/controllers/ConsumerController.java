package ru.komiufps.cartridges.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.komiufps.cartridges.entity.*;
import ru.komiufps.cartridges.service.*;
import ru.komiufps.cartridges.utils.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/consumer")
public class ConsumerController {

    private final CartridgeService cartridgeService;
    private final CartridgeForConsumerService cartridgeForConsumerService;
    private final OrderForConsumerService orderForConsumerService;
    private final CartridgeChecker cartridgeChecker;
    private final StatusCartridgeAfterConsumerService statusCartridgeAfterConsumerService;
    private final ConsumerService consumerService;

    @GetMapping("/getAllConsumers")
    public List<Consumer> getAllConsumers() {
        return consumerService.getAllConsumers();
    }

    @GetMapping("/getAllStatusAfterConsumer")
    public List<StatusCartridgeAfterConsumer> getAllStatusAfterConsumer() {
        return statusCartridgeAfterConsumerService.getAllStatusAfterConsumer();
    }

    @PutMapping("/closeOrders")
    public BaseResponse closeOrders(@RequestBody List<CartridgeForConsumer> cartridgeForConsumerList) {

        cartridgeForConsumerList.forEach(cartridgeForConsumer -> {
            cartridgeForConsumer.setDateTheCartridgeWasReturn(LocalDateTime.now());
            Cartridge cartridge = cartridgeForConsumer.getCartridge();

            if (statusCartridgeAfterConsumerService.findById(cartridgeForConsumer.getStatusCartridgeAfterConsumer().getId()).isGoodStatus())
                cartridge.setStateCartridge(StateCartridge.EmptyInStock);
            else
                cartridge.setStateCartridge(StateCartridge.DefectiveInStock);

            cartridgeService.save(cartridge);
            cartridgeForConsumerService.save(cartridgeForConsumer);
        });

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
                    CartridgeForConsumer cartridgeForConsumer = new CartridgeForConsumer();
                    cartridgeForConsumer.setOrderForConsumer(orderForConsumer);
                    cartridgeForConsumer.setCartridge(cartridge);
                    cartridge.setStateCartridge(StateCartridge.IssueToConsumer);

                    cartridgeService.save(cartridge);
                    cartridgeForConsumerService.save(cartridgeForConsumer);
                });


        return BaseResponse
                .builder()
                .message("Заказ успешно создан.")
                .build();

    }

    @GetMapping("/getOrder")
    public CartridgeForConsumer getOrder(@RequestParam(value = "serialNumber") String serialNumber) {
        Cartridge cartridge = cartridgeService.getCartridgeBySerialNumber(serialNumber);
        try {
            cartridgeChecker.check(cartridge, "CartridgeAfterConsumer");
        } catch (CheckerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }

        return cartridgeForConsumerService
                .getOrderForCartridge(cartridge)
                .get();

    }

}
