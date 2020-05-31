package com.fashionista.api.services;

import com.fashionista.api.dtos.response.FavouriteResponse;
import com.fashionista.api.dtos.response.ProductListResponse;
import com.fashionista.api.entities.Favourite;
import com.fashionista.api.entities.Product;
import com.fashionista.api.entities.User;
import com.fashionista.api.exceptions.GenericException;
import com.fashionista.api.repositories.FavouriteRepository;
import com.fashionista.api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteService {
    private FavouriteRepository favouriteRepository;
    private ProductRepository productRepository;

    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository, ProductRepository productRepository) {
        this.favouriteRepository = favouriteRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> getAllFavourites(User user) {
        List<Favourite> favourites = favouriteRepository.findByUser(user);
        List<Product> products = favourites.stream().map(Favourite::getProduct).collect(Collectors.toList());
        return ResponseEntity.ok(new ProductListResponse(0, 0, products));
    }

    public ResponseEntity<?> toggleFavourite(User user, String productId) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new GenericException("Product not found.", HttpStatus.BAD_REQUEST);
        }

        Favourite existingFavourite = favouriteRepository.findFirstByUserAndProduct(user, product);

        boolean isFavourite = false;

        if (existingFavourite != null) {
            favouriteRepository.delete(existingFavourite);
        } else {
            Favourite favourite = new Favourite();
            favourite.setProduct(product);
            favourite.setUser(user);
            favouriteRepository.save(favourite);
            isFavourite = true;
        }

        return ResponseEntity.ok(new FavouriteResponse(isFavourite));
    }

    public ResponseEntity<?> getFavouriteProduct(User user, String productId) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new GenericException("Product not found.", HttpStatus.BAD_REQUEST);
        }

        Favourite existingFavourite = favouriteRepository.findFirstByUserAndProduct(user, product);

        boolean isFavourite = false;

        if (existingFavourite != null) {
            isFavourite = true;
        }

        return ResponseEntity.ok(new FavouriteResponse(isFavourite));
    }
}
