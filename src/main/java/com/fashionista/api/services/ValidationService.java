package com.fashionista.api.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationService {
    public ResponseEntity<?> validate(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            map.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(map);
    }
}
