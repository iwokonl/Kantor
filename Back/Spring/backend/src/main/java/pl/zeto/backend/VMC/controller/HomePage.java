package pl.zeto.backend.VMC.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {

    @GetMapping("/")
    public String home() {
        return "Strona główna Kantoru Walutowego";
    }
}
