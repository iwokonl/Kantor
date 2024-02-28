package pl.zeto.backend.VMC.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomePageController {

    @GetMapping("/")
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("username", auth.getName());
            return "logged in home"; // Przekieruj zalogowanych użytkowników na stronę główną
        }
        return "home"; // Nazwa pliku bez rozszerzenia .html z katalogu resources/templates
    }
}
