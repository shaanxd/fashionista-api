package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Cart;
import com.fashionista.api.entities.PurchaseItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class CartItemResponse {
    private String id;
    private int quantity = 0;
    private double totalPrice = 0.0;
    private String size;
    private ProductResponse product;

    static CartItemResponse transformToDto(Cart cart) {
        ProductResponse productResponse = ProductResponse.transformWithoutAll(cart.getProduct());

        return new CartItemResponse(
                cart.getId(),
                cart.getQuantity(),
                cart.getTotalPrice(),
                cart.getSize(),
                productResponse
        );
    }

    static CartItemResponse transformToDto(PurchaseItem purchase) {
        ProductResponse productResponse = ProductResponse.transformWithoutAll(purchase.getProduct());

        return new CartItemResponse(
                purchase.getId(),
                purchase.getQuantity(),
                purchase.getTotalPrice(),
                purchase.getSize(),
                productResponse
        );
    }
}
