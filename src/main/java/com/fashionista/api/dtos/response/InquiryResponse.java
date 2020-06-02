package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Inquiry;
import com.fashionista.api.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryResponse {
    private String id;
    private String title;
    private String description;
    private OwnerResponse owner;
    private Date inquiryDate;
    private ProductResponse product;
    private List<ReplyResponse> replies = new ArrayList<>();

    public InquiryResponse(String id, String title, String description, OwnerResponse owner, Date inquiryDate, List<ReplyResponse> replies) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.inquiryDate = inquiryDate;
        this.replies = replies;
    }

    public static InquiryResponse transformToDto(Inquiry inquiry) {
        User user = inquiry.getUser();
        OwnerResponse owner = new OwnerResponse(user.getId(), user.getFullName());
        List<ReplyResponse> replies = inquiry.getReplies().stream().map(ReplyResponse::transformToDto).collect(Collectors.toList());

        return new InquiryResponse(
                inquiry.getId(),
                inquiry.getTitle(),
                inquiry.getDescription(),
                owner,
                inquiry.getUpdatedAt(),
                replies
        );
    }

    public static InquiryResponse transformToDtoWithProduct(Inquiry inquiry) {
        User user = inquiry.getUser();
        OwnerResponse owner = new OwnerResponse(user.getId(), user.getFullName());
        List<ReplyResponse> replies = inquiry.getReplies().stream().map(ReplyResponse::transformToDto).collect(Collectors.toList());

        return new InquiryResponse(
                inquiry.getId(),
                inquiry.getTitle(),
                inquiry.getDescription(),
                owner,
                inquiry.getUpdatedAt(),
                ProductResponse.transformWithoutAll(inquiry.getProduct()),
                replies
        );
    }
}
