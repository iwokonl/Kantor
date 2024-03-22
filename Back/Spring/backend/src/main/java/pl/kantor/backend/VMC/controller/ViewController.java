package pl.kantor.backend.VMC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }
}