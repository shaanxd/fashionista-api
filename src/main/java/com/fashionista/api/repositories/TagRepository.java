package com.fashionista.api.repositories;

import com.fashionista.api.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, String> {
    long countByIdIn(List<String> ids);

    List<Tag> findAllByNameContaining(String name);

    Page<Tag> findAllByType(String type, Pageable pageable);
}
