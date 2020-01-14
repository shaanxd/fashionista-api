package com.fashionista.api.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class OwnerResponse {
    private String id;
    private String fullName;

    OwnerResponse(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}
