package pl.kantor.backend.MVC.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppUserTest {

    @Test
    public void testAppUser() {
        // Utwórz instancję AppUser
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");

        // Sprawdź, czy pola są poprawnie ustawione
        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());
    }
}