package pl.zeto.backend.VMC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.zeto.backend.VMC.model.AppUser;
import pl.zeto.backend.VMC.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public AppUser addUser(@RequestBody AppUser user) {
        return userService.addUser(user);
    }

    @GetMapping("/getUser/{id}")
    public AppUser getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
