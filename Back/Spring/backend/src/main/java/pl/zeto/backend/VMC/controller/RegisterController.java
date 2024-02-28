package pl.zeto.backend.VMC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.zeto.backend.VMC.model.AppUser;
import pl.zeto.backend.VMC.model.Role;
import pl.zeto.backend.VMC.repository.UserRepo;

@Controller
public class RegisterController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private pl.zeto.backend.VMC.service.UserService userService;

    // Zwraca widok HTML
    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    // Przetwarza formularz rejestracji i zwraca stronę HTML
    @PostMapping("/process_register")
    public String processRegister(AppUser user) {

        userService.addUser(user);
        return "register_success";
    }

    // Endpoint API do obsługi rejestracji za pomocą JSON
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<Object> processRegisterApi(@RequestBody AppUser user) {
        user.setRole(Role.USER);
        userRepo.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
}