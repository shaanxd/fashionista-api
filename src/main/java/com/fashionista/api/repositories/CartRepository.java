package com.fashionista.api.repositories;

import com.fashionista.api.entities.Cart;
import com.fashionista.api.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, String> {
    List<Cart> findByUser(User user);
}
