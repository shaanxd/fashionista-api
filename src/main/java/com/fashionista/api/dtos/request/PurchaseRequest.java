package com.fashionista.api.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PurchaseRequest {
    @Size(min = 1, message = "Cart should contain atleast one item")
    private List<String> cart = new ArrayList<>();
}
