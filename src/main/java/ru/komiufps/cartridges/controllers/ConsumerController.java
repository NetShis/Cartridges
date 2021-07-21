package ru.komiufps.cartridges.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.komiufps.cartridges.entity.Consumer;
import ru.komiufps.cartridges.service.ConsumerService;

@Controller
//@RestController
@RequiredArgsConstructor
@RequestMapping("/consumer")
public class ConsumerController {

    private final ConsumerService consumerService;

    /*
    @PostMapping("/add")
    public void addConsumer (@RequestBody Consumer consumer) {
        consumerService.addConsumer(consumer);
    }
    */




}
