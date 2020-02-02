package com.fashionista.api.controllers;

import com.fashionista.api.dtos.request.CartRequest;
import com.fashionista.api.services.CartService;
import com.fashionista.api.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.fashionista.api.constants.RouteConstants.*;

@RestController
@RequestMapping(CART_ROOT)
@CrossOrigin("*")
public class CartController {
    private CartService cartService;
    private ValidationService validationService;

    @Autowired
    public CartController(CartService cartService, ValidationService validationService) {
        this.cartService = cartService;
        this.validationService = validationService;
    }

    @GetMapping(CART_GET)
    public ResponseEntity<?> getCart(Authentication authentication) {
        return cartService.getUserCart(validationService.validateUser(authentication));
    }

    @PostMapping(CART_ADD_PRODUCT)
    public ResponseEntity<?> addProduct(@Valid @RequestBody CartRequest cartRequest, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return cartService.createCart(cartRequest.getProductId(), cartRequest.transformToEntity(), validationService.validateUser(authentication));
    }

    @PostMapping(CART_DELETE_CART)
    public ResponseEntity<?> deleteProduct(@PathVariable String id, Authentication authentication) {
        return cartService.deleteCartItem(id, validationService.validateUser(authentication));
    }
}
