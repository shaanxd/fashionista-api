package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Purchase;
import com.fashionista.api.entities.PurchaseItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PurchaseListResponse {
    private int total = 0;
    private int current = 0;
    private List<PurchaseResponse> purchases = new ArrayList<>();

    public PurchaseListResponse(int total, int current, List<Purchase> purchases) {
        this.total = (total == 0) ? total : total - 1;
        this.current = current;
        for (Purchase purchase : purchases) {
            List<PurchaseItem> purchaseItems = purchase.getItems();
            this.purchases.add(
                    new PurchaseResponse(
                            purchase.getId(),
                            purchase.getTotalPrice(),
                            purchaseItems.size(),
                            purchase.getUpdatedAt(),
                            purchaseItems
                    )
            );
        }
    }
}
