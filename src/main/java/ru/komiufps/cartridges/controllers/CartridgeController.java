package ru.komiufps.cartridges.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.СartridgeModel;
import ru.komiufps.cartridges.entity.СartridgeStatus;
import ru.komiufps.cartridges.service.CartridgeService;
import ru.komiufps.cartridges.service.СartridgeModelService;
import ru.komiufps.cartridges.service.СartridgeStatusService;


@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/cartridge")

public class CartridgeController {
    private final CartridgeService cartridgeService;
    private final СartridgeModelService cartridgeModelService;
    private final СartridgeStatusService cartridgeStatusService;

    @PostMapping("/add")
    public void addCartridge (@RequestBody Cartridge cartridge) {
        cartridgeService.addCartridge(cartridge);
    }

    @PostMapping("/addModel")
    public void addСartridgeModel (@RequestBody СartridgeModel cartridgeModel) {
        cartridgeModelService.addСartridgeModel(cartridgeModel);
    }

    @PostMapping("/addStatus")
    public void addСartridgeStatus (@RequestBody СartridgeStatus cartridgeStatus) {
        cartridgeStatusService.addСartridgeStatus(cartridgeStatus);
    }



}
