package com.fashionista.api.controllers;

import com.fashionista.api.dtos.request.PurchaseRequest;
import com.fashionista.api.entities.Review;
import com.fashionista.api.exceptions.GenericException;
import com.fashionista.api.services.FileStorageService;
import com.fashionista.api.services.ProductService;
import com.fashionista.api.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.fashionista.api.constants.RouteConstants.*;

@RestController
@RequestMapping(PRODUCT_ROOT)
@CrossOrigin("*")
public class ProductController {
    private ProductService productService;
    private FileStorageService fileStorageService;
    private ValidationService validationService;

    @Autowired
    public ProductController(ProductService productService, FileStorageService fileStorageService, ValidationService validationService) {
        this.productService = productService;
        this.fileStorageService = fileStorageService;
        this.validationService = validationService;
    }

    @GetMapping(PRODUCT_GET)
    public ResponseEntity<?> getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @GetMapping(PRODUCTS_GET)
    public ResponseEntity<?> getProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @GetMapping(PRODUCT_IMAGE_GET)
    public ResponseEntity<?> getImage(@PathVariable String filename, HttpServletRequest request) {
        return fileStorageService.getImage(filename, request);
    }

    @PostMapping(PRODUCT_ADD_REVIEW)
    public ResponseEntity<?> addReview(@PathVariable String id, @Valid @RequestBody Review review, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return productService.addReview(id, review, validationService.validateUser(authentication));
    }

    @GetMapping(PRODUCT_GET_REVIEWS)
    public ResponseEntity<?> getReviews(@PathVariable String id, Pageable pageable) {
        return productService.getProductReviews(id, pageable);
    }

    @GetMapping(PRODUCTS_SEARCH)
    public ResponseEntity<?> searchProducts(@PathVariable String name, Pageable pageable) {
        return productService.getProductsByName(name, pageable);
    }

    @PostMapping(PRODUCT_GET_BY_TAG)
    public ResponseEntity<?> getProductsByTag(@Valid @RequestBody PurchaseRequest purchaseRequest, BindingResult result, Pageable pageable) {
        if (result.hasErrors()) {
            validationService.validate(result);
        }
        if (purchaseRequest.getCart().size() < 1) {
            throw new GenericException("Cart cannot be empty", HttpStatus.BAD_REQUEST);
        }
        return productService.getProductsByTags(purchaseRequest.getCart(), pageable);
    }
}
