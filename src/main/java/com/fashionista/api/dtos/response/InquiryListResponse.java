package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Inquiry;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class InquiryListResponse {
    private int total = 0;
    private int current = 0;
    private List<InquiryResponse> inquiries = new ArrayList<>();

    public InquiryListResponse(int total, int current, List<Inquiry> inquiries, boolean isAdmin) {
        this.total = (total == 0) ? total : total - 1;
        this.current = current;
        for (Inquiry inquiry : inquiries) {
            this.inquiries.add(InquiryResponse.transformToDtoWithProduct(inquiry));
        }
    }
}
