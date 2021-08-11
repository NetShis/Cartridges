package ru.komiufps.cartridges.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.komiufps.cartridges.entity.*;
import ru.komiufps.cartridges.service.*;
import ru.komiufps.cartridges.utils.BaseResponse;
import ru.komiufps.cartridges.utils.CartridgeChecker;
import ru.komiufps.cartridges.utils.CheckerException;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/refueller")
public class RefuellerController {

    private final RefuellerService refuellerService;
    private final OrderForRefuellerService orderForRefuellerService;
    private final CartridgeForRefuellerService cartridgeForRefuellerService;
    private final StatusCartridgeAfterRefuellerService statusCartridgeAfterRefuellerService;
    private final CartridgeService cartridgeService;
    private final CartridgeChecker cartridgeChecker;

    @PostMapping("/createOrder")
    public BaseResponse createOrder(@RequestBody List<Cartridge> cartridgeList) {
        OrderForRefueller orderForRefueller = new OrderForRefueller();
        orderForRefueller.setRefueller(refuellerService.getDefaultRefueller());

        orderForRefuellerService.save(orderForRefueller);
        cartridgeList.forEach(cartridge -> {
            CartridgeForRefueller cartridgeForRefueller = new CartridgeForRefueller();
            cartridgeForRefueller.setOrderForRefueller(orderForRefueller);
            cartridgeForRefueller.setCartridge(cartridge);
            cartridgeForRefuellerService.save(cartridgeForRefueller);
        });

        return BaseResponse
                .builder()
                .message("Поручение на заправку успешно сформировано!")
                .build();
    }

    @GetMapping("/getDefaultRefueller")
    public Refueller getDefaultRefueller() {
        return refuellerService.getDefaultRefueller();
    }

    @GetMapping("/getAllStatusAfterRefueller")
    public List<StatusCartridgeAfterRefueller> getAllStatusAfterRefueller() {
        return statusCartridgeAfterRefuellerService.getAllStatusAfterRefueller();
    }

    @GetMapping("/getOrder")
    public CartridgeForRefueller getOrder(@RequestParam(value = "serialNumber") String serialNumber) {
        Cartridge cartridge = cartridgeService.getCartridgeBySerialNumber(serialNumber);
        try {
            cartridgeChecker.check(cartridge, "CartridgesFromRefueller");
        } catch (CheckerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }

        return cartridgeForRefuellerService
                .getCartridgeForRefueller(cartridge)
                .get();
    }

    @PutMapping("/closeOrders")
    public BaseResponse closeOrders(@RequestBody List<CartridgeForRefueller> cartridgeForRefuellerList) {
        cartridgeForRefuellerService.closeOrders(cartridgeForRefuellerList);

        return BaseResponse
                .builder()
                .message("Заказ успешно закрыт.")
                .build();
    }
}
