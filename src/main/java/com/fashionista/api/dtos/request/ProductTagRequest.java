package com.fashionista.api.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTagRequest {
    @Size(min = 1, message = "Cart should contain atleast one item")
    private List<String> cart = new ArrayList<>();
}
