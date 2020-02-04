package com.fashionista.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private int stock = 0;
    private double price = 0.0;
    private String thumbnail;
    private String images;

    @Formula("(SELECT IFNULL((SELECT AVG(r.rating) FROM Review r WHERE id = r.product_id), 0))")
    private double avgRating = 0.0;

    @Formula("(SELECT IFNULL((SELECT COUNT(*) FROM Review r WHERE id = r.product_id AND r.rating = 1), 0))")
    private int oneRating = 0;

    @Formula("(SELECT IFNULL((SELECT COUNT(*) FROM Review r WHERE id = r.product_id AND r.rating = 2), 0))")
    private int twoRating = 0;

    @Formula("(SELECT IFNULL((SELECT COUNT(*) FROM Review r WHERE id = r.product_id AND r.rating = 3), 0))")
    private int threeRating = 0;

    @Formula("(SELECT IFNULL((SELECT COUNT(*) FROM Review r WHERE id = r.product_id AND r.rating = 4), 0))")
    private int fourRating = 0;

    @Formula("(SELECT IFNULL((SELECT COUNT(*) FROM Review r WHERE id = r.product_id AND r.rating = 5), 0))")
    private int fiveRating = 0;

    @Formula("(SELECT IFNULL((SELECT COUNT(*) FROM Review r WHERE id = r.product_id), 0))")
    private int totalRating = 0;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductTag> productTags = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cart> cart = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PurchaseItem> purchaseItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    @CreationTimestamp
    @JsonIgnore
    private Date createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private Date updatedAt;

    public Product(String name, String description, int stock, double price) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

    public List<String> retrieveImagesArray() {
        if (StringUtils.hasText(this.images)) {
            return Arrays.asList(this.images.split(","));
        }
        return Collections.emptyList();
    }

    public void setImagesFromArray(List<String> images) {
        this.images = String.join(",", images);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
