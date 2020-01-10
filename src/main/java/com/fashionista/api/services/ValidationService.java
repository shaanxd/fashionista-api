package com.fashionista.api.services;

import com.fashionista.api.exceptions.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

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
}
