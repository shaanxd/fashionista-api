package com.fashionista.api.controllers;

import com.fashionista.api.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fashionista.api.constants.RouteConstants.TAG_ROOT;
import static com.fashionista.api.constants.RouteConstants.TAG_SEARCH;

@RestController
@RequestMapping(TAG_ROOT)
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
}
