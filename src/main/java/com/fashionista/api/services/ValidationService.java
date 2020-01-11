package com.fashionista.api.services;

import com.fashionista.api.entities.User;
import com.fashionista.api.exceptions.GenericException;
import com.fashionista.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationService {
    private UserRepository userRepository;

    @Autowired
    public ValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> validate(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            map.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(map);
    }

    public void validateFile(MultipartFile file) {
        if (file.getContentType() == null || (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png"))) {
            throw new GenericException("Unsupported file " + file.getOriginalFilename() + ". Please try again with valid images.", HttpStatus.BAD_REQUEST);
        }
    }

    public void validateFiles(MultipartFile[] files) {
        for (MultipartFile file : files) {
            validateFile(file);
        }
    }

    public User validateUser(Authentication authentication) {
        User user = null;
        if (authentication != null) {
            String userId = authentication.getName();
            if (StringUtils.hasText(userId)) {
                return userRepository.findById(userId).orElse(null);
            }
        }

        return user;
    }
}
