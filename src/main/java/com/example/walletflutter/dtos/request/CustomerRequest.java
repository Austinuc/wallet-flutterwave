package com.example.walletflutter.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CustomerRequest {

    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    private String name;
    private String phonenumber;
}
