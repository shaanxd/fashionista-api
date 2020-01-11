package com.fashionista.api.controllers;

import com.fashionista.api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fashionista.api.constants.RouteConstants.*;

@RestController
@RequestMapping(PRODUCT_ROOT)
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(PRODUCT_GET)
    public ResponseEntity<?> getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @GetMapping(PRODUCTS_GET)
    public ResponseEntity<?> getProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }

}
