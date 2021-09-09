package ru.komiufps.cartridges.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.komiufps.cartridges.entity.Cartridge;
import ru.komiufps.cartridges.entity.CartridgeForConsumer;
import ru.komiufps.cartridges.entity.CartridgeForRefueller;
import ru.komiufps.cartridges.service.CartridgeForConsumerService;
import ru.komiufps.cartridges.service.CartridgeForRefuellerService;
import ru.komiufps.cartridges.service.CartridgeService;
import ru.komiufps.cartridges.utils.StateCartridge;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reports")
public class ReportsController {
    private final CartridgeService cartridgeService;
    private final CartridgeForConsumerService cartridgeForConsumerService;
    private final CartridgeForRefuellerService cartridgeForRefuellerService;

    @GetMapping("/cartridge_count_report")
    public String cartridgeCountReport(Model model) {
        List<Cartridge> allCartridge = cartridgeService.getAllCartridge();

        Map<String, Map<String, Integer>> mapModelStatus = new HashMap<>();
        String cartridgeModelStr;

        for (Cartridge cartridge : allCartridge) {
            cartridgeModelStr = cartridge.getCartridgeModel().getCartridgeModel();

            if (!mapModelStatus.containsKey(cartridgeModelStr)) {
                Map<String, Integer> statusCounts = new LinkedHashMap<>();
                for (StateCartridge stateCartridge : StateCartridge.values())
                    statusCounts.put(stateCartridge.getDescriptionInRussian(), 0);
                statusCounts.put("Всего", 0);

                mapModelStatus.put(cartridgeModelStr, statusCounts);
            }

            String getDescriptionInRussian = cartridge.getStateCartridge().getDescriptionInRussian();
            mapModelStatus
                    .get(cartridgeModelStr)
                    .put(getDescriptionInRussian
                            , mapModelStatus
                                    .get(cartridgeModelStr)
                                    .get(getDescriptionInRussian) + 1);

            mapModelStatus
                    .get(cartridgeModelStr)
                    .put("Всего", mapModelStatus
                            .get(cartridgeModelStr)
                            .get("Всего") + 1);
        }

        model.addAttribute("mapModelStatus", mapModelStatus);

        return "cartridge_count_report";

    }

    @GetMapping("/report_on_the_movement_of_cartridges")
    public String reportOnTheMovementOfCartridges(@RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime start
            , @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime end, Model model) {

        String cartridgeModelStr;
        Map<String, Integer> issuedCartridges = new HashMap<>();
        List<CartridgeForConsumer> cartridgeForConsumerList
                = cartridgeForConsumerService.findAllByDateIssuedCartridges(start, end);

        for (CartridgeForConsumer cartridgeForConsumer : cartridgeForConsumerList) {
            cartridgeModelStr = cartridgeForConsumer.getCartridge().getCartridgeModel().getCartridgeModel();
            if (!issuedCartridges.containsKey(cartridgeModelStr))
                issuedCartridges.put(cartridgeModelStr, 0);

            issuedCartridges.put(cartridgeModelStr, issuedCartridges.get(cartridgeModelStr) + 1);
        }

        model.addAttribute("issuedCartridges", issuedCartridges);

        Map<String, Integer> refilledCartridges = new HashMap<>();
        List<CartridgeForRefueller> cartridgeForRefuellerList
                = cartridgeForRefuellerService.findAllByDateRefilledCartridges(start, end);


        for (CartridgeForRefueller cartridgeForRefueller : cartridgeForRefuellerList) {
            cartridgeModelStr = cartridgeForRefueller.getCartridge().getCartridgeModel().getCartridgeModel();
            if (!refilledCartridges.containsKey(cartridgeModelStr))
                refilledCartridges.put(cartridgeModelStr, 0);
            refilledCartridges.put(cartridgeModelStr, refilledCartridges.get(cartridgeModelStr) + 1);
        }

        model.addAttribute("refilledCartridges", refilledCartridges);


        Map<String, Integer> defectiveCartridges = new HashMap<>();
        List<CartridgeForConsumer> defectiveCartridgeList
                = cartridgeForConsumerService.findAllByDateDefectiveCartridges(start, end);

        for (CartridgeForConsumer cartridgeForConsumer : defectiveCartridgeList) {
            cartridgeModelStr = cartridgeForConsumer.getCartridge().getCartridgeModel().getCartridgeModel();
            if (!defectiveCartridges.containsKey(cartridgeModelStr))
                defectiveCartridges.put(cartridgeModelStr, 0);
            defectiveCartridges.put(cartridgeModelStr, defectiveCartridges.get(cartridgeModelStr) + 1);

        }

        model.addAttribute("defectiveCartridges", defectiveCartridges);

        return "report_on_the_movement_of_cartridges";
    }

}