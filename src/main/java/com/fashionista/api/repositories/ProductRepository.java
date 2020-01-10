package com.fashionista.api.repositories;

import com.fashionista.api.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
}
