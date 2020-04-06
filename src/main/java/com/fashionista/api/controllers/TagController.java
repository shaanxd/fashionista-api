package com.fashionista.api.controllers;

import com.fashionista.api.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.fashionista.api.constants.RouteConstants.*;

@RestController
@RequestMapping(TAG_ROOT)
@CrossOrigin("*")
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(TAG_SEARCH)
    public ResponseEntity<?> searchTags(@PathVariable String name) {
        return tagService.searchTag(name);
    }

    @GetMapping(TAG_GET)
    public ResponseEntity<?> getTags(@RequestParam(required = false) String type, Pageable pageable) {
        return tagService.getTags(type, pageable);
    }

    @GetMapping(TAG_GET_ALL)
    public ResponseEntity<?> getAllTags() {
        return tagService.getAllTags();
    }
}
