package ru.komiufps.cartridges.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
    @GetMapping("/")
    public String greeting(Model model) {
        return "index";
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        return "reports";
    }

}
