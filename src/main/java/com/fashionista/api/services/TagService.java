package com.fashionista.api.services;

import com.fashionista.api.entities.Tag;
import com.fashionista.api.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public ResponseEntity<?> createTag(Tag tag) {
        return ResponseEntity.ok(tagRepository.save(tag));
    }
}
