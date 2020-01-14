package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReviewListResponse {
    private int total = 0;
    private int current = 0;
    private List<ReviewResponse> reviews = new ArrayList<>();

    public ReviewListResponse(int total, int current, List<Review> reviews) {
        this.total = (total == 0) ? total : total - 1;
        this.current = current;
        for (Review review : reviews) {
            this.reviews.add(ReviewResponse.transformToDto(review));
        }
    }
}
