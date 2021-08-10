package ru.komiufps.cartridges.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForRefueller;
import ru.komiufps.cartridges.entity.OrderForRefueller;
import ru.komiufps.cartridges.entity.Refueller;
import ru.komiufps.cartridges.service.CartridgeForRefuellerService;
import ru.komiufps.cartridges.service.OrderForRefuellerService;
import ru.komiufps.cartridges.service.RefuellerService;
import ru.komiufps.cartridges.utils.BaseResponse;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/refueller")
public class RefuellerController {

    private final RefuellerService refuellerService;
    private final OrderForRefuellerService orderForRefuellerService;
    private final CartridgeForRefuellerService cartridgeForRefuellerService;

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

}
