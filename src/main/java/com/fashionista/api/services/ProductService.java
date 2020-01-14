package com.fashionista.api.services;

import com.fashionista.api.dtos.response.ProductListResponse;
import com.fashionista.api.dtos.response.ProductResponse;
import com.fashionista.api.dtos.response.ReviewListResponse;
import com.fashionista.api.entities.Product;
import com.fashionista.api.entities.ProductTag;
import com.fashionista.api.entities.Review;
import com.fashionista.api.entities.User;
import com.fashionista.api.exceptions.GenericException;
import com.fashionista.api.repositories.ProductRepository;
import com.fashionista.api.repositories.ProductTagRepository;
import com.fashionista.api.repositories.ReviewRepository;
import com.fashionista.api.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {
    private FileStorageService fileStorageService;
    private ProductRepository productRepository;
    private TagRepository tagRepository;
    private ProductTagRepository productTagRepository;
    private EntityManager entityManager;
    private ReviewRepository reviewRepository;

    @Autowired
    public ProductService(FileStorageService fileStorageService, ProductRepository productRepository, TagRepository tagRepository, ProductTagRepository productTagRepository, EntityManager entityManager, ReviewRepository reviewRepository) {
        this.fileStorageService = fileStorageService;
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
        this.productTagRepository = productTagRepository;
        this.entityManager = entityManager;
        this.reviewRepository = reviewRepository;
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
        ReviewListResponse reviewListResponse = new ReviewListResponse(0, 0, Collections.emptyList());
        return ResponseEntity.ok(ProductResponse.transformWithAll(responseProduct, reviewListResponse));
    }

    public ResponseEntity<?> getProduct(String id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new GenericException("Product not found.", HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(0, 5);
        Page<Review> reviews = reviewRepository.findByProduct(product, pageable);
        return ResponseEntity.ok(ProductResponse.transformWithAll(product,
                new ReviewListResponse(reviews.getTotalPages(), reviews.getNumber(), reviews.getContent())
        ));
    }

    public ResponseEntity<?> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return ResponseEntity.ok(new ProductListResponse(products.getTotalPages(), products.getNumber(), products.getContent()));
    }

    @Transactional
    public ResponseEntity<?> addReview(String id, Review updatedReview, User user) {
        if (user == null) {
            throw new GenericException("User not found.", HttpStatus.UNAUTHORIZED);
        }
        Product product = productRepository.findById(id).orElseThrow(() -> new GenericException("Product not found.", HttpStatus.NOT_FOUND));
        Review review = reviewRepository.findByProductAndUser(product, user);

        if (review == null) {
            review = updatedReview;
            review.setProduct(product);
            review.setUser(user);
        } else {
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
        }

        reviewRepository.save(review);

        entityManager.flush();
        entityManager.refresh(product);

        Page<Review> reviews = reviewRepository.findByProduct(product, PageRequest.of(0, 5));

        return ResponseEntity.ok(ProductResponse.transformWithAll(product,
                new ReviewListResponse(reviews.getTotalPages(), reviews.getNumber(), reviews.getContent())
        ));
    }

    public ResponseEntity<?> getProductReviews(String id, Pageable pageable) {
        Product product = productRepository.findById(id).orElseThrow(() -> new GenericException("Product not found.", HttpStatus.NOT_FOUND));
        Page<Review> reviews = reviewRepository.findByProduct(product, pageable);
        return ResponseEntity.ok(new ReviewListResponse(reviews.getTotalPages(), reviews.getNumber(), reviews.getContent()));
    }
}
