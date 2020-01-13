package com.fashionista.api.repositories;

import com.fashionista.api.entities.Purchase;
import com.fashionista.api.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, String> {
    Page<Purchase> findByUser(User user, Pageable pageable);
}
