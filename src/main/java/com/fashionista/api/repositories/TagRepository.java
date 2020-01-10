package com.fashionista.api.repositories;

import com.fashionista.api.entities.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, String> {
    long countByIdIn(List<String> ids);
}
