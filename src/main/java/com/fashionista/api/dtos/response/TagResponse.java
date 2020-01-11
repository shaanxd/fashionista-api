package com.fashionista.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class TagResponse {
    private String id;
    private String name;
    private String type;
    private String description;

    TagResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
