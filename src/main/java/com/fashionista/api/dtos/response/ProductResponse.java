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
    private List<String> images = new ArrayList<>();
    private List<TagResponse> tags = new ArrayList<>();

    private ProductResponse(String id, String name, String description, int stock, double price, String thumbnail, List<String> images, List<ProductTag> productTags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.thumbnail = thumbnail;
        this.images = images;
        for (ProductTag productTag : productTags) {
            this.tags.add(new TagResponse(productTag.getTag().getId(), productTag.getTag().getName()));
        }
    }

    public static ProductResponse transformToDto(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getStock(),
                product.getPrice(),
                product.getThumbnail(),
                product.retrieveImagesArray(),
                product.getProductTags()
        );
    }
}
