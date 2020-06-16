package com.fashionista.api.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PurchaseRequest {
    @Size(min = 1, message = "Cart should contain atleast one item")
    private List<String> cart = new ArrayList<>();
    @NotBlank(message = "Name cannot be empty.")
    private String name;
    @NotBlank(message = "Address cannot be empty.")
    private String address;
    @NotBlank(message = "City cannot be empty.")
    private String city;
    @NotBlank(message = "Country cannot be empty.")
    private String country;
    @NotBlank(message = "Payment method cannot be empty.")
    private String paymentMethod;
}
