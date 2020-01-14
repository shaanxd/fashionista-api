package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class ReviewResponse {
    private String id;
    private String title;
    private String description;
    private double rating = 0.0;
    private OwnerResponse ownerResponse;

    private ReviewResponse(String id, String title, String description, double rating, OwnerResponse ownerResponse) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.ownerResponse = ownerResponse;
    }

    static ReviewResponse transformToDto(Review review) {
        OwnerResponse ownerResponse = new OwnerResponse(review.getUser().getId(), review.getUser().getFullName());

        return new ReviewResponse(
                review.getId(),
                review.getTitle(),
                review.getDescription(),
                review.getRating(),
                ownerResponse
        );
    }
}
