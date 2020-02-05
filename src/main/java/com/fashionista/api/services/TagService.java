package com.fashionista.api.services;

import com.fashionista.api.dtos.response.TagListResponse;
import com.fashionista.api.entities.Tag;
import com.fashionista.api.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class TagService {
    private TagRepository tagRepository;
    private FileStorageService fileStorageService;

    @Autowired
    public TagService(TagRepository tagRepository, FileStorageService fileStorageService) {
        this.tagRepository = tagRepository;
        this.fileStorageService = fileStorageService;
    }

    public ResponseEntity<?> createTag(Tag tag, MultipartFile file) {
        String image = fileStorageService.store(file);
        tag.setImage(image);
        return ResponseEntity.ok(tagRepository.save(tag));
    }

    public ResponseEntity<?> searchTag(String name) {
        return ResponseEntity.ok((tagRepository.findAllByNameContaining(name)));
    }

    public ResponseEntity<?> getTags(String type, Pageable pageable) {
        Page<Tag> page;

        if (type != null) {
            page = tagRepository.findAllByType(type, pageable);
        } else {
            page = tagRepository.findAll(pageable);
        }

        return ResponseEntity.ok(new TagListResponse(page.getTotalPages(), page.getNumber(), page.getContent()));
    }
}
