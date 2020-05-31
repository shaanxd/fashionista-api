package com.fashionista.api.repositories;

import com.fashionista.api.entities.Favourite;
import com.fashionista.api.entities.Product;
import com.fashionista.api.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavouriteRepository extends CrudRepository<Favourite, String> {
    List<Favourite> findByUser(User user);

    Favourite findFirstByUserAndProduct(User user, Product product);
}
