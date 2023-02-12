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
@RequestMapping("api/v1/payment")
@AllArgsConstructor
public class Payment {

    private final AppUtil appUtil;
    private final PaymentServices paymentService;
    @PostMapping("/get-payment-link")
    public ResponseEntity<ApiResponse> getPaymentLink(@RequestBody  CustomerRequest customerRequest) {
        AppUtil appUtil = AppUtil.getInstance();
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .amount(customerRequest.getAmount())
                .currency("NGN")
                .customer(customerRequest)
                .tx_ref(appUtil.generateSerialNumber("TX_REF_"))
                .redirect_url("http://localhost/api/v1/payment/payment-callback/")
                .build();

        return ResponseEntity.ok(paymentService.getPaymentLink(paymentDetails));
    }

    @GetMapping("/payment-callback/")
    public ApiResponse<VerifyTransaction> afterPaymentRedirect(@RequestParam Map<String, String> redirectParams) {
        System.out.println(redirectParams);
        appUtil.log("Payment callback activated");
        return paymentService.verifyPayment(redirectParams.get("transaction_id"));
    }
}
