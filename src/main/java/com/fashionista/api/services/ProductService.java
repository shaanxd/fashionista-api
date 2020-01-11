package com.fashionista.api.services;

import com.fashionista.api.dtos.response.ProductResponse;
import com.fashionista.api.entities.Product;
import com.fashionista.api.entities.ProductTag;
import com.fashionista.api.exceptions.GenericException;
import com.fashionista.api.repositories.ProductRepository;
import com.fashionista.api.repositories.ProductTagRepository;
import com.fashionista.api.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private FileStorageService fileStorageService;
    private ProductRepository productRepository;
    private TagRepository tagRepository;
    private ProductTagRepository productTagRepository;
    private EntityManager entityManager;

    @Autowired
    public ProductService(FileStorageService fileStorageService, ProductRepository productRepository, TagRepository tagRepository, ProductTagRepository productTagRepository, EntityManager entityManager) {
        this.fileStorageService = fileStorageService;
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
        this.productTagRepository = productTagRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public ResponseEntity<?> createProduct(Product product, MultipartFile file, MultipartFile[] files, List<String> tags) {
        long tagCount = tagRepository.countByIdIn(tags);
        if (tagCount != tags.size()) {
            throw new GenericException("Invalid tag provided. Please provide valid tags.", HttpStatus.BAD_REQUEST);
        }
        String thumbnail = fileStorageService.store(file);
        List<String> images = fileStorageService.storeMultiple(files);

        product.setImagesFromArray(images);
        product.setThumbnail(thumbnail);
        Product savedProduct = productRepository.save(product);
        List<ProductTag> productTags = new ArrayList<>();
        for (String tag : tags) {
            tagRepository.findById(tag).ifPresent(foundTag -> productTags.add(new ProductTag(savedProduct, foundTag)));
        }
        productTagRepository.saveAll(productTags);

        entityManager.flush();
        entityManager.clear();

        Product responseProduct = productRepository.findById(savedProduct.getId()).orElse(savedProduct);
        return ResponseEntity.ok(ProductResponse.transformToDto(responseProduct));
    }
}
