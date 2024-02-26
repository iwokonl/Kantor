package pl.zeto.backend.VMC.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zeto.backend.VMC.model.AppUser;
import pl.zeto.backend.VMC.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    public AppUser addUser(AppUser user) {
        // Tutaj można dodać logikę walidacji lub hashowania hasła
        return userRepository.save(user);
    }

}
