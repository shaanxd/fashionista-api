package com.fashionista.api.services;

import com.fashionista.api.dtos.response.CartResponse;
import com.fashionista.api.entities.Cart;
import com.fashionista.api.entities.Product;
import com.fashionista.api.entities.User;
import com.fashionista.api.exceptions.GenericException;
import com.fashionista.api.repositories.CartRepository;
import com.fashionista.api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> createCart(String productId, Cart cart, User user) {
        if (user == null) {
            throw new GenericException("User not found.", HttpStatus.UNAUTHORIZED);
        }
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new GenericException("Product not found", HttpStatus.BAD_REQUEST);
        }
        if (product.getStock() < cart.getQuantity()) {
            throw new GenericException("Product only has " + product.getStock() + " pieces available.", HttpStatus.BAD_REQUEST);
        }
        cart.setTotalPrice(product.getPrice() * cart.getQuantity());
        cart.setProduct(product);
        cart.setUser(user);
        cartRepository.save(cart);

        List<Cart> userCart = cartRepository.findByUser(user);
        return ResponseEntity.ok(new CartResponse(userCart));
    }
}
