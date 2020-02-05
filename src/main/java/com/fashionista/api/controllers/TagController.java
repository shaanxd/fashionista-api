package com.fashionista.api.controllers;

import com.fashionista.api.services.FileStorageService;
import com.fashionista.api.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.fashionista.api.constants.RouteConstants.*;

@RestController
@RequestMapping(TAG_ROOT)
@CrossOrigin("*")
public class TagController {
    private TagService tagService;
    private FileStorageService fileStorageService;

    @Autowired
    public TagController(TagService tagService, FileStorageService fileStorageService) {
        this.tagService = tagService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping(TAG_SEARCH)
    public ResponseEntity<?> searchTags(@PathVariable String name) {
        return tagService.searchTag(name);
    }

    @GetMapping(TAG_IMAGE_GET)
    public ResponseEntity<?> getTagImage(@PathVariable String filename, HttpServletRequest request) {
        return fileStorageService.getImage(filename, request);
    }

    @GetMapping(TAG_GET)
    public ResponseEntity<?> getTags(@RequestParam(required = false) String type, Pageable pageable) {
        return tagService.getTags(pageable);
    }
}
