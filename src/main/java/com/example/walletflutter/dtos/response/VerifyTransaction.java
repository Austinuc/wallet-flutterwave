package com.example.walletflutter.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyTransaction {
    private Long id;
    private String status;
    private String tx_ref;
    private BigDecimal amount;
    private String currency;
    private String charged_amount;
    private String processor_response;
    private String auth_model;
    private String payment_type;
    private String narration;
}
