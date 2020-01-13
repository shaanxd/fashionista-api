package com.fashionista.api.services;

import com.fashionista.api.entities.*;
import com.fashionista.api.exceptions.GenericException;
import com.fashionista.api.repositories.CartRepository;
import com.fashionista.api.repositories.PurchaseItemRepository;
import com.fashionista.api.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {
    private PurchaseRepository purchaseRepository;
    private PurchaseItemRepository purchaseItemRepository;
    private CartRepository cartRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, PurchaseItemRepository purchaseItemRepository, CartRepository cartRepository) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public ResponseEntity<?> purchaseOrders(List<String> ids, User user) {
        if (user == null) {
            throw new GenericException("User not found.", HttpStatus.UNAUTHORIZED);
        }
        List<Cart> cart = (List<Cart>) cartRepository.findAllById(ids);
        if (cart.size() < 1) {
            throw new GenericException("Cart doesn't contain items.", HttpStatus.BAD_REQUEST);
        }
        double total = 0.0;
        Purchase purchase = purchaseRepository.save(new Purchase(total, user));
        List<PurchaseItem> purchases = new ArrayList<>();

        for (Cart item : cart) {
            if (!item.getUser().getId().equals(user.getId())) {
                throw new GenericException("Cart doesn't belong to authenticated user.", HttpStatus.FORBIDDEN);
            }
            Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new GenericException("Product " + product.getName() + " doesn't have sufficient stocks.", HttpStatus.BAD_REQUEST);
            }
            product.setStock(product.getStock() - item.getQuantity());
            total += item.getTotalPrice();
            purchases.add(new PurchaseItem(product, item.getQuantity(), item.getTotalPrice(), item.getSize(), purchase));
            cartRepository.delete(item);
        }
        purchase.setTotalPrice(total);
        purchase.setItems(purchases);

        return ResponseEntity.ok(purchase);
    }
}
