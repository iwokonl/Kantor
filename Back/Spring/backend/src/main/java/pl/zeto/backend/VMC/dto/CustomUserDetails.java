package pl.zeto.backend.VMC.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.zeto.backend.VMC.model.AppAccount;
import pl.zeto.backend.VMC.model.AppUser;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private AppUser appAccount;

    public CustomUserDetails(AppUser account) {
        this.appAccount = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(appAccount.getRole().name()));
    }

    @Override
    public String getPassword() {
        return appAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return appAccount.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Implementacja pozostałych metod interfejsu UserDetails...
}