package com.ex.customerinfo.model;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @NotNull(message = "Customer reference cannot be null")
    @Positive(message = "Customer reference must be a positive number")
    private Long customerRef;

    @NotBlank(message = "Customer name cannot be blank")
    @Size(max = 255, message = "Customer name must be at most 255 characters")
    private String customerName;

    @NotBlank(message = "Address line 1 cannot be blank")
    @Size(max = 255, message = "Address line 1 must be at most 255 characters")
    private String addressLine1;

    @Size(max = 255, message = "Address line 2 must be at most 255 characters")
    private String addressLine2;

    @NotBlank(message = "Town cannot be blank")
    @Size(max = 255, message = "Town must be at most 255 characters")
    private String town;

    @Size(max = 255, message = "County must be at most 255 characters")
    private String county;

    @NotBlank(message = "Country cannot be blank")
    @Size(max = 255, message = "Country must be at most 255 characters")
    private String country;

    @NotBlank(message = "Postcode cannot be blank")
    @Size(max = 20, message = "Postcode must be at most 20 characters")
    private String postcode;
}
