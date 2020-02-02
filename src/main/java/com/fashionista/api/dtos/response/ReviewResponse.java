package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
class ReviewResponse {
    private String id;
    private String title;
    private String description;
    private double rating = 0.0;
    private Date reviewDate;
    private OwnerResponse owner;

    private ReviewResponse(String id, String title, String description, double rating, Date reviewDate, OwnerResponse owner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.owner = owner;
    }

    static ReviewResponse transformToDto(Review review) {
        OwnerResponse owner = new OwnerResponse(review.getUser().getId(), review.getUser().getFullName());

        return new ReviewResponse(
                review.getId(),
                review.getTitle(),
                review.getDescription(),
                review.getRating(),
                review.getUpdatedAt(),
                owner
        );
    }
}
