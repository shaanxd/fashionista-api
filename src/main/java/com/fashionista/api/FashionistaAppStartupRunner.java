package com.fashionista.api;

import com.fashionista.api.entities.User;
import com.fashionista.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.fashionista.api.constants.AuthConstants.AUTH_ADMIN;

@Component
public class FashionistaAppStartupRunner implements CommandLineRunner {
    private UserRepository repository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public FashionistaAppStartupRunner(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            User user = new User();

            user.setFullName("Fashionista Admin");
            user.setEmail("admin@fashionista.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole(AUTH_ADMIN);
            user.setConfirmPassword("password");

            repository.save(user);
        }
    }
}
