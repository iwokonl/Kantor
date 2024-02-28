package pl.zeto.backend.VMC.controller;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.zeto.backend.VMC.exeption.UserAlreadyExistsException;
import pl.zeto.backend.VMC.model.AppAccount;
import pl.zeto.backend.VMC.model.AppUser;
import pl.zeto.backend.VMC.model.Role;
import pl.zeto.backend.VMC.repository.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.zeto.backend.VMC.service.AccountService;

@Controller
public class RegisterController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private pl.zeto.backend.VMC.service.UserService userService;
    @Autowired
    private AccountService accountService;
    // Zwraca widok HTML
    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/"; // Podobnie przekieruj zalogowanych użytkowników
        }
//        this.model = model;
        model.addAttribute("user", new AppUser());
        return "register";
    }

    // Przetwarza formularz rejestracji i zwraca stronę HTML
    @PostMapping("/process_register")
    public String processRegister(AppUser user, RedirectAttributes redirectAttributes, AppAccount account) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/"; // Podobnie przekieruj zalogowanych użytkowników
        }
        try {

            userService.addUser(user); // Próba dodania użytkownika
            account.setUser(user);
            account.setCurrency("PLN");
            accountService.addAccount(account);
             // Próba dodania konta użytkownika
            return "/register_success"; // Przekieruj na stronę sukcesu rejestracji
        } catch (Exception e) {
            String errorMessage = "Użytkownik istnieje lub inny błąd rejestracji";
            redirectAttributes.addFlashAttribute("registrationError", errorMessage);
            return "redirect:/register"; // Przekieruj z powrotem na stronę rejestracji z komunikatem o błędzie
        }
    }


    // Endpoint API do obsługi rejestracji za pomocą JSON
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<Object> processRegisterApi(@RequestBody AppUser user) {
        user.setRole(Role.USER);
        userService.addUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }


}
