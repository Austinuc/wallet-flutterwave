package com.example.walletflutter.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerRequest {

    @NotNull(message = "Email must be provide")
    private String email;
//    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    private String name;
    private String phonenumber;
}
