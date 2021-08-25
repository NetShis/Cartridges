package ru.komiufps.cartridges.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeModel;
import ru.komiufps.cartridges.service.CartridgeService;
import ru.komiufps.cartridges.utils.CartridgeChecker;
import ru.komiufps.cartridges.utils.StateCartridge;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reports")
public class ReportsController {
    private final CartridgeService cartridgeService;
    private final CartridgeChecker cartridgeChecker;

    @GetMapping("/cartridge_count_report")
    public String cartridgeCountReport(Model model) {
        List<Cartridge> allCartridge = cartridgeService.getAllCartridge();

        Map<CartridgeModel, EnumMap<StateCartridge, Integer>> mapModelStatus = new HashMap<>();

        for (Cartridge cartridge : allCartridge) {

            if (!mapModelStatus.containsKey(cartridge.getCartridgeModel())) {
                EnumMap<StateCartridge, Integer> statusCounts = new EnumMap(StateCartridge.class);
                for (StateCartridge stateCartridge : StateCartridge.values())
                    statusCounts.put(stateCartridge, 0);

                mapModelStatus.put(cartridge.getCartridgeModel(), statusCounts);
            }
            mapModelStatus
                    .get(cartridge.getCartridgeModel())
                    .put(cartridgeChecker.getStateCartridge(cartridge), mapModelStatus
                            .get(cartridge.getCartridgeModel())
                            .get(cartridgeChecker.getStateCartridge(cartridge)) + 1);

        }

        model.addAttribute("mapModelStatus", mapModelStatus);

        return "cartridge_count_report";

    }
}