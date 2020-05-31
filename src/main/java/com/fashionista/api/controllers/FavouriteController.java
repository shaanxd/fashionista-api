package com.fashionista.api.controllers;

import com.fashionista.api.services.FavouriteService;
import com.fashionista.api.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.fashionista.api.constants.RouteConstants.*;

@RestController
@RequestMapping(FAVOURITE_ROOT)
@CrossOrigin("*")
public class FavouriteController {
    private FavouriteService favouriteService;
    private ValidationService validationService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService, ValidationService validationService) {
        this.favouriteService = favouriteService;
        this.validationService = validationService;
    }

    @GetMapping(FAVOURITE_GET_ALL)
    public ResponseEntity<?> getAllFavourites(Authentication authentication) {
        return favouriteService.getAllFavourites(validationService.validateUser(authentication));
    }

    @PostMapping(FAVOURITE_TOGGLE)
    public ResponseEntity<?> toggleFavourite(@PathVariable String id, Authentication authentication) {
        return favouriteService.toggleFavourite(validationService.validateUser(authentication), id);
    }

    @GetMapping(FAVOURITE_GET_PRODUCT)
    public ResponseEntity<?> getFavouriteProduct(@PathVariable String id, Authentication authentication) {
        return favouriteService.getFavouriteProduct(validationService.validateUser(authentication), id);
    }
}
