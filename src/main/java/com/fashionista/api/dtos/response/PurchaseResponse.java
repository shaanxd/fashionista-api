package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.PurchaseItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PurchaseResponse {
    private String id;
    private double totalPrice = 0.0;
    private int numberOfItems = 0;
    private List<CartItemResponse> purchases = new ArrayList<>();

    public PurchaseResponse(String id, double totalPrice, int numberOfItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.numberOfItems = numberOfItems;
        this.purchases = null;
    }

    public PurchaseResponse(String id, double totalPrice, int numberOfItems, List<PurchaseItem> purchasesList) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.numberOfItems = numberOfItems;
        for (PurchaseItem purchase : purchasesList) {
            this.purchases.add(CartItemResponse.transformToDto(purchase));
        }
    }
}
