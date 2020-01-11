package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductListResponse {
    private long total;
    private long current;
    private List<ProductResponse> products = new ArrayList<>();

    public ProductListResponse(long total, long current, List<Product> products) {
        this.total = (total == 0) ? total : total - 1;
        this.current = current;
        for (Product product : products) {
            this.products.add(ProductResponse.transformWithoutAll(product));
        }
    }
}
