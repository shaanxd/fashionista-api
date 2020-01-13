package com.fashionista.api.repositories;

import com.fashionista.api.entities.PurchaseItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseItemRepository extends CrudRepository<PurchaseItem, String> {
}
