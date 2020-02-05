package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllTagsResponse {
    private List<TagResponse> brands = new ArrayList<>();
    private List<TagResponse> genders = new ArrayList<>();
    private List<TagResponse> types = new ArrayList<>();

    public AllTagsResponse(List<Tag> brands, List<Tag> genders, List<Tag> types) {
        for (Tag tag : brands) {
            this.brands.add(new TagResponse(tag.getId(), tag.getName(), tag.getType(), tag.getImage()));
        }
        for (Tag tag : genders) {
            this.genders.add(new TagResponse(tag.getId(), tag.getName(), tag.getType(), tag.getImage()));
        }
        for (Tag tag : types) {
            this.types.add(new TagResponse(tag.getId(), tag.getName(), tag.getType(), tag.getImage()));
        }
    }
}
