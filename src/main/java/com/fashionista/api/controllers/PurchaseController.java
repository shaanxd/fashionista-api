package com.fashionista.api.controllers;

import com.fashionista.api.dtos.request.PurchaseRequest;
import com.fashionista.api.services.PurchaseService;
import com.fashionista.api.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.fashionista.api.constants.RouteConstants.*;

@RestController
@RequestMapping(PURCHASES_ROOT)
@CrossOrigin("*")
public class PurchaseController {
    private PurchaseService purchaseService;
    private ValidationService validationService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, ValidationService validationService) {
        this.purchaseService = purchaseService;
        this.validationService = validationService;
    }

    @PostMapping(PURCHASE_CART)
    public ResponseEntity<?> purchaseCart(@Valid @RequestBody PurchaseRequest purchaseRequest, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            validationService.validate(result);
        }
        return purchaseService.purchaseOrders(purchaseRequest, validationService.validateUser(authentication));
    }

    @GetMapping(PURCHASES_GET)
    public ResponseEntity<?> getPurchases(Authentication authentication, Pageable pageable) {
        return purchaseService.getOrders(validationService.validateUser(authentication), pageable);
    }

    @GetMapping(PURCHASE_GET)
    public ResponseEntity<?> getPurchase(@PathVariable String id, Authentication authentication) {
        return purchaseService.getOrder(id, validationService.validateUser(authentication));
    }

}
