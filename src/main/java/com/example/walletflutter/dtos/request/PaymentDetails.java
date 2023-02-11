package com.example.walletflutter.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDetails {

    private String tx_ref;
    private BigDecimal amount;
    private String currency;
    private String redirect_url;
    private CustomerRequest customer;
}
