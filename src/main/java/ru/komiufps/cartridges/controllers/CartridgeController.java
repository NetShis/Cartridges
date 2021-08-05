package ru.komiufps.cartridges.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.СartridgeModel;
import ru.komiufps.cartridges.entity.СartridgeStatus;
import ru.komiufps.cartridges.service.CartridgeForOrderService;
import ru.komiufps.cartridges.service.CartridgeService;
import ru.komiufps.cartridges.service.СartridgeModelService;
import ru.komiufps.cartridges.service.СartridgeStatusService;
import ru.komiufps.cartridges.utils.BaseResponse;
import ru.komiufps.cartridges.utils.CartridgeChecker;
import ru.komiufps.cartridges.utils.CheckerException;

import java.util.List;


@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/cartridge")

public class CartridgeController {
    private final CartridgeService cartridgeService;
    private final СartridgeModelService cartridgeModelService;
    private final СartridgeStatusService cartridgeStatusService;
    private final CartridgeForOrderService cartridgeForOrderService;
    private final CartridgeChecker cartridgeChecker;

    @PostMapping("/add")
    public void addCartridge(@RequestBody String cartridgeList) {
        System.out.println(cartridgeList);
    }

    @PostMapping("/addModel")
    public void addСartridgeModel(@RequestBody СartridgeModel cartridgeModel) {
        cartridgeModelService.addСartridgeModel(cartridgeModel);
    }

    @PostMapping("/addStatus")
    public void addСartridgeStatus(@RequestBody СartridgeStatus cartridgeStatus) {
        cartridgeStatusService.addСartridgeStatus(cartridgeStatus);
    }

    @GetMapping("/getAllStatus")
    public List<СartridgeStatus> getAllStatus() {
        return cartridgeStatusService.getAllStatus();
    }

    @GetMapping("/getCartrigeBySerialNumber")
    public Cartridge getCartrigeBySerialNumber(@RequestParam(value = "operation") String operation,
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
        cartridgeService.deregisterCartridge(cartridgeList);

        return BaseResponse.builder()
                .message("Картриджы успешно выведены из эксплуатации.")
                .build();
    }

}
