package com.example.walletflutter.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDetails {

    private String tx_ref;
    private BigDecimal amount;
    private String currency;
    private String redirect_url;
    private CustomerRequest customer;
}
