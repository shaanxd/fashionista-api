package com.fashionista.api.controllers;

import com.fashionista.api.dtos.request.ProductTagRequest;
import com.fashionista.api.entities.Inquiry;
import com.fashionista.api.entities.Reply;
import com.fashionista.api.entities.Review;
import com.fashionista.api.exceptions.GenericException;
import com.fashionista.api.services.ProductService;
import com.fashionista.api.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.fashionista.api.constants.RouteConstants.*;

@RestController
@RequestMapping(PRODUCT_ROOT)
@CrossOrigin("*")
public class ProductController {
    private ProductService productService;
    private ValidationService validationService;

    @Autowired
    public ProductController(ProductService productService, ValidationService validationService) {
        this.productService = productService;
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
    public ResponseEntity<?> getProductsByTag(@Valid @RequestBody ProductTagRequest purchaseRequest, BindingResult result, Pageable pageable) {
        if (result.hasErrors()) {
            validationService.validate(result);
        }
        if (purchaseRequest.getCart().size() < 1) {
            throw new GenericException("Cart cannot be empty", HttpStatus.BAD_REQUEST);
        }
        return productService.getProductsByTags(purchaseRequest.getCart(), pageable);
    }

    @PostMapping(PRODUCT_ADD_INQUIRY)
    public ResponseEntity<?> addInquiry(@PathVariable String id, @Valid @RequestBody Inquiry inquiry, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return productService.addInquiry(id, inquiry, validationService.validateUser(authentication));
    }

    @GetMapping(PRODUCT_GET_INQUIRIES)
    public ResponseEntity<?> getInquiries(@PathVariable String id, Pageable pageable) {
        return productService.getInquiries(id, pageable);
    }

    @PostMapping(PRODUCT_ADD_REPLY)
    public ResponseEntity<?> addReply(@PathVariable String id, @Valid @RequestBody Reply reply, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return productService.addReply(id, validationService.validateUser(authentication), reply);
    }
}
