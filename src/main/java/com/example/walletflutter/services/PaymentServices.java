package com.example.walletflutter.services;

import com.example.walletflutter.dtos.request.CustomerRequest;
import com.example.walletflutter.dtos.request.PaymentDetails;
import com.example.walletflutter.dtos.response.ApiResponse;
import com.example.walletflutter.dtos.response.VerifyTransaction;

public interface PaymentServices {
    ApiResponse getPaymentLink(PaymentDetails paymentDetails);

    ApiResponse<VerifyTransaction> verifyPayment(String trasactionId);
}
