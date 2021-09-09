package ru.komiufps.cartridges.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MainController {
    @GetMapping("/")
    public String greeting(Model model, HttpServletRequest request) {
        if ("127.0.0.1".equals(request.getRemoteAddr()))
            return "index";
        else throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Доступ разрешен только с главного компьютера!");
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        return "reports";
    }

}
