package com.fashionista.api.dtos.request;

import com.fashionista.api.entities.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CartRequest {
    @NotBlank(message = "Product is required.")
    private String productId;
    @Min(value = 1, message = "Cart should have at least 1 product.")
    private int quantity;
    @NotBlank(message = "Product size is required.")
    private String size;

    public Cart transformToEntity() {
        return new Cart(this.quantity, this.size);
    }
}
