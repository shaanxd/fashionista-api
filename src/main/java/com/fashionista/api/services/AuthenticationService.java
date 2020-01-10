package com.fashionista.api.services;

import com.fashionista.api.dtos.response.AuthenticationResponse;
import com.fashionista.api.entities.User;
import com.fashionista.api.exceptions.GenericException;
import com.fashionista.api.repositories.UserRepository;
import com.fashionista.api.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.fashionista.api.constants.AuthConstants.AUTH_ADMIN;
import static com.fashionista.api.constants.SecurityConstants.VALID_DURATION;

@Service
public class AuthenticationService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtTokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<AuthenticationResponse> registerUser(User user) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new GenericException("Passwords must match.", HttpStatus.BAD_REQUEST);
        }
        User foundUser = userRepository.findUserByEmail(user.getEmail());
        if (foundUser != null) {
            throw new GenericException("Email already exists.", HttpStatus.BAD_REQUEST);
        }
        user.setRole(AUTH_ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        String token = tokenProvider.generateToken(savedUser);

        return ResponseEntity.ok(new AuthenticationResponse(
                token,
                user.getId(),
                user.getFullName(),
                user.getRole(),
                VALID_DURATION / 1000
        ));
    }

    public ResponseEntity<AuthenticationResponse> authenticateUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String token = tokenProvider.generateToken(user);
        return ResponseEntity.ok(new AuthenticationResponse(
                token,
                user.getId(),
                user.getFullName(),
                user.getRole(),
                VALID_DURATION / 1000
        ));
    }
}
