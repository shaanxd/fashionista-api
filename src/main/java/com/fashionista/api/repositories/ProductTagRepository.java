package com.fashionista.api.repositories;

import com.fashionista.api.entities.ProductTag;
import org.springframework.data.repository.CrudRepository;

public interface ProductTagRepository extends CrudRepository<ProductTag, String> {

}
