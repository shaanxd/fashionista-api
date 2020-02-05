package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TagListResponse {
    private int total = 0;
    private int current = 0;
    private List<TagResponse> tags = new ArrayList<>();

    public TagListResponse(int total, int current, List<Tag> tags) {
        this.total = (total == 0) ? total : total - 1;
        this.current = current;
        for (Tag tag : tags) {
            this.tags.add(new TagResponse(tag.getId(), tag.getName(), tag.getType(), tag.getImage()));
        }
    }
}
