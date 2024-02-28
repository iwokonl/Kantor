package pl.zeto.backend.VMC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/")
    public String home() {
        return "index"; // Nazwa pliku bez rozszerzenia .html z katalogu resources/templates
    }
}
