package com.fashionista.api.controllers;

import com.fashionista.api.dtos.request.LoginRequest;
import com.fashionista.api.entities.User;
import com.fashionista.api.services.AuthenticationService;
import com.fashionista.api.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.fashionista.api.constants.RouteConstants.*;

@RestController
@RequestMapping(AUTH_ROOT)
public class AuthenticationController {
    private ValidationService validationService;
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(ValidationService validationService, AuthenticationService authenticationService) {
        this.validationService = validationService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(AUTH_REGISTER)
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return authenticationService.registerUser(user);
    }

    @PostMapping(AUTH_LOGIN)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return authenticationService.authenticateUser(request.getEmail(), request.getPassword());
    }
}
