package com.fashionista.api.dtos.request;

import com.fashionista.api.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Product name is required.")
    private String name;
    @NotBlank(message = "Product description is required.")
    private String description;
    @Min(value = 1, message = "Product stock is required.")
    private int stock = 0;
    @Min(value = 1, message = "Product price is required.")
    private double price = 0.0;
    @Size(min = 1, message = "Product should belong to at least one tag")
    private List<String> tags = new ArrayList<>();
    @NotNull(message = "Product thumbnail is required.")
    private MultipartFile thumbnail;
    @NotNull(message = "Product should at least contain one image.")
    private MultipartFile[] images;

    public Product transformToEntity() {
        return new Product(this.name, this.description, this.stock, this.price);
    }
}
