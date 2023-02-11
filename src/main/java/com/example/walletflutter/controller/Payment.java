package com.example.walletflutter.controller;

import com.example.walletflutter.dtos.request.CustomerRequest;
import com.example.walletflutter.dtos.request.PaymentDetails;
import com.example.walletflutter.dtos.response.ApiResponse;
import com.example.walletflutter.dtos.response.VerifyTransaction;
import com.example.walletflutter.entity.Transaction;
import com.example.walletflutter.services.PaymentServices;
import com.example.walletflutter.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/vi/payment")
@AllArgsConstructor
public class Payment {

    private final PaymentServices paymentService;
    @PostMapping("/get-payment-link")
    public ResponseEntity<ApiResponse> getPaymentLink(@RequestBody  CustomerRequest customerRequest) {
        AppUtil appUtil = AppUtil.getInstance();
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .amount(customerRequest.getAmount())
                .currency("NGN")
                .customer(customerRequest)
                .tx_ref(appUtil.generateSerialNumber("TX_REF_"))
                .redirect_url("https://decagonhq.com/")
                .build();

        return ResponseEntity.ok(paymentService.getPaymentLink(paymentDetails));
    }

    @GetMapping("/payment-callback/")
    public ApiResponse<VerifyTransaction> afterPaymentRedirect(@RequestParam(name = "transaction_id") String transaction_id) {

        return paymentService.verifyPayment(transaction_id);
    }
}
