package com.fashionista.api.repositories;

import com.fashionista.api.entities.Inquiry;
import com.fashionista.api.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, String> {
    Page<Inquiry> findByProduct(Product product, Pageable pageable);
}
