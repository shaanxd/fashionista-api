package com.fashionista.api.controllers;

import com.fashionista.api.dtos.request.ProductRequest;
import com.fashionista.api.dtos.request.TagRequest;
import com.fashionista.api.services.ProductService;
import com.fashionista.api.services.TagService;
import com.fashionista.api.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.fashionista.api.constants.RouteConstants.*;

@RestController
@RequestMapping(ADMIN_ROOT)
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private TagService tagService;
    private ValidationService validationService;
    private ProductService productService;

    @Autowired
    public AdminController(TagService tagService, ValidationService validationService, ProductService productService) {
        this.tagService = tagService;
        this.validationService = validationService;
        this.productService = productService;
    }

    @PostMapping(ADMIN_CREATE_TAG)
    public ResponseEntity<?> createTag(@Valid TagRequest tagRequest, BindingResult result) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        validationService.validateFile(tagRequest.getImage());
        return tagService.createTag(tagRequest.transformToEntity(), tagRequest.getImage());
    }

    @PostMapping(ADMIN_CREATE_PRODUCT)
    public ResponseEntity<?> createProduct(@Valid ProductRequest productRequest, BindingResult result) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        validationService.validateFile(productRequest.getThumbnail());
        validationService.validateFiles(productRequest.getImages());

        return productService.createProduct(
                productRequest.transformToEntity(), productRequest.getThumbnail(),
                productRequest.getImages(), productRequest.getTags()
        );
    }
}
