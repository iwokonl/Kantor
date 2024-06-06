package org.example.gateway.repository;


import org.example.gateway.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
;

import java.util.Optional;


public interface UserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

}