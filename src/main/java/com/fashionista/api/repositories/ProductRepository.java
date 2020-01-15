package com.fashionista.api.repositories;

import com.fashionista.api.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {
    Page<Product> findAllByNameContaining(String name, Pageable pageable);
}
