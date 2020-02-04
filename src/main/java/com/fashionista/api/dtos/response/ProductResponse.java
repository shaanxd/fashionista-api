package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Product;
import com.fashionista.api.entities.ProductTag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private int stock = 0;
    private double price = 0.0;
    private String thumbnail;
    private double avgRating = 0.0;
    private List<String> images = new ArrayList<>();
    private List<TagResponse> tags = new ArrayList<>();
    private ReviewListResponse reviews;
    private Integer oneStars = null;
    private Integer twoStars = null;
    private Integer threeStars = null;
    private Integer fourStars = null;
    private Integer fiveStars = null;
    private Integer totalStars = null;

    private ProductResponse(String id, String name, String description, int stock, double price, String thumbnail, Double avgRating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.thumbnail = thumbnail;
        this.avgRating = avgRating;
        this.images = null;
        this.tags = null;
    }

    private ProductResponse(String id, String name, String description, int stock, double price, String thumbnail,
                            double avgRating, List<String> images, List<ProductTag> productTags, ReviewListResponse reviews,
                            int oneStars, int twoStars, int threeStars, int fourStars, int fiveStars, int totalStars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.thumbnail = thumbnail;
        this.avgRating = avgRating;
        this.images = images;
        for (ProductTag productTag : productTags) {
            this.tags.add(new TagResponse(productTag.getTag().getId(), productTag.getTag().getName()));
        }
        this.reviews = reviews;
        this.oneStars = oneStars;
        this.twoStars = twoStars;
        this.threeStars = threeStars;
        this.fourStars = fourStars;
        this.fiveStars = fiveStars;
        this.totalStars = totalStars;
    }

    static ProductResponse transformWithoutAll(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getStock(),
                product.getPrice(),
                product.getThumbnail(),
                product.getAvgRating()
        );
    }

    public static ProductResponse transformWithAll(Product product, ReviewListResponse reviews) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getStock(),
                product.getPrice(),
                product.getThumbnail(),
                product.getAvgRating(),
                product.retrieveImagesArray(),
                product.getProductTags(),
                reviews,
                product.getOneRating(),
                product.getTwoRating(),
                product.getThreeRating(),
                product.getFourRating(),
                product.getFiveRating(),
                product.getTotalRating()
        );
    }
}
