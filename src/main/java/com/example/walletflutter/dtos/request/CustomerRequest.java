package com.example.walletflutter.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CustomerRequest {

    @NotNull(message = "Email must be provide")
    private String email;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    private String name;
    private String phonenumber;
}
