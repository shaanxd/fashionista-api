package com.fashionista.api.controllers;

import com.fashionista.api.dtos.request.PurchaseRequest;
import com.fashionista.api.services.PurchaseService;
import com.fashionista.api.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.fashionista.api.constants.RouteConstants.PURCHASES_ROOT;
import static com.fashionista.api.constants.RouteConstants.PURCHASE_CART;

@RestController
@RequestMapping(PURCHASES_ROOT)
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
        return purchaseService.purchaseOrders(purchaseRequest.getCart(), validationService.validateUser(authentication));
    }
}
