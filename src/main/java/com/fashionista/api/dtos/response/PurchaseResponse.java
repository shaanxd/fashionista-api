package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.PurchaseItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class PurchaseResponse {
    private String id;
    private double totalPrice = 0.0;
    private int numberOfItems = 0;
    private Date orderedAt;
    private List<CartItemResponse> purchases = new ArrayList<>();

    public PurchaseResponse(String id, double totalPrice, int numberOfItems, Date orderedAt) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.numberOfItems = numberOfItems;
        this.orderedAt = orderedAt;
        this.purchases = null;
    }

    public PurchaseResponse(String id, double totalPrice, int numberOfItems, Date orderedAt, List<PurchaseItem> purchasesList) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.numberOfItems = numberOfItems;
        this.orderedAt = orderedAt;
        for (PurchaseItem purchase : purchasesList) {
            this.purchases.add(CartItemResponse.transformToDto(purchase));
        }
    }
}
