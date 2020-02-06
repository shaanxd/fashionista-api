package com.fashionista.api.repositories;

import com.fashionista.api.entities.Product;
import com.fashionista.api.entities.ProductTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ProductTagRepository extends CrudRepository<ProductTag, String> {
    @Query(value = "SELECT a.product FROM #{#entityName} a WHERE tag_id IN :ids GROUP BY product_id HAVING COUNT(tag_id) = :count")
    Page<Product> getTaggedProducts(@Param("ids") Set<String> ids, Pageable pageable, @Param("count") long count);
}
