package com.fashionista.api.repositories;

import com.fashionista.api.entities.Product;
import com.fashionista.api.entities.Review;
import com.fashionista.api.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, String> {
    Page<Review> findByProduct(Product product, Pageable pageable);

    Review findByProductAndUser(Product product, User user);
}
