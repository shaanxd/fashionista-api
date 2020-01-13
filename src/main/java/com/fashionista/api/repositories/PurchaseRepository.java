package com.fashionista.api.repositories;

import com.fashionista.api.entities.Purchase;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, String> {
}
