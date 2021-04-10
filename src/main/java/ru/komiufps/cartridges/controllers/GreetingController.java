package ru.komiufps.cartridges.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.komiufps.cartridges.models.Cartridges;
import ru.komiufps.cartridges.models.СartridgeModel;
import ru.komiufps.cartridges.repo.СartridgeModelRepository;

@Controller
public class GreetingController {
    @Autowired
    private СartridgeModelRepository cartridgeModelRepository;

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/addCartridge")
    public String addCartridgeGet(Model model) {
        Iterable<СartridgeModel> cartridgeModels = cartridgeModelRepository.findAll();
        model.addAttribute("cartridgeModels",cartridgeModels);
        return "addCartridge";
    }

    @PostMapping("/addCartridge")
    public String addCartridgePost(@RequestParam String serialNumbers, Model model) {
        System.out.println(serialNumbers);
        return "redirect:/addCartridge";
    }

    @GetMapping("/addModelOfCartridge")
    public String addModelOfCartridgeGet(Model model) {
        return "addModelOfCartridge";
    }

    @PostMapping("/addModelOfCartridge")
    public String addModelOfCartridgePost(@RequestParam String cartridgeModelName, Model model) {
        СartridgeModel cartridgeModel = new СartridgeModel(cartridgeModelName);
        cartridgeModelRepository.save(cartridgeModel);
        return "redirect:/addCartridge";
    }

}