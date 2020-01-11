package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CartResponse {
    private int numberOfItems = 0;
    private double totalPrice = 0.0;
    private List<CartItemResponse> items = new ArrayList<>();

    public CartResponse(List<Cart> cart) {
        this.numberOfItems = cart.size();
        double total = 0.0;
        for (Cart item : cart) {
            total += item.getTotalPrice();
            items.add(CartItemResponse.transformToDto(item));
        }
        this.totalPrice = total;
    }
}
