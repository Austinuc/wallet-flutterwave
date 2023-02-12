package com.example.walletflutter.services.impl;

import com.example.walletflutter.dtos.request.PaymentDetails;
import com.example.walletflutter.dtos.response.ApiResponse;
import com.example.walletflutter.dtos.response.VerifyTransaction;
import com.example.walletflutter.entity.Transaction;
import com.example.walletflutter.enums.Currencies;
import com.example.walletflutter.enums.TransactionStatus;
import com.example.walletflutter.repository.TransactionRepository;
import com.example.walletflutter.services.PaymentServices;
import com.example.walletflutter.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentServices {
    private final RestTemplate restTemplate;
    private final TransactionRepository transactionRepository;

    @Value("${app.FLW_SECRET_KEY}")
    private String FLW_SECRET_KEY;
    @Override
    public ApiResponse getPaymentLink(PaymentDetails paymentDetails) {
        String url = "https://api.flutterwave.com/v3/payments";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + FLW_SECRET_KEY);


        HttpEntity<PaymentDetails> entity = new HttpEntity<>(paymentDetails, headers);

        Transaction transaction = Transaction.builder()
                .amount(paymentDetails.getAmount())
                .status(TransactionStatus.PENDING.name())
                .userId(1L)
                .txRef(paymentDetails.getTx_ref())
                .currency("NGN")
                .build();

        transactionRepository.save(transaction);

        return restTemplate.exchange(url, HttpMethod.POST,entity, ApiResponse.class).getBody();
    }

    @Override
    public ApiResponse verifyPayment(String trasactionId) {

        String url = "https://api.flutterwave.com/v3/transactions/"+trasactionId+"/verify";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + FLW_SECRET_KEY);

        HttpEntity entity = new HttpEntity<>(headers);

        ApiResponse apiResponse = restTemplate.exchange(url, HttpMethod.GET,entity, ApiResponse.class).getBody();

        AppUtil appUtil = AppUtil.getInstance();

        assert apiResponse != null;
        VerifyTransaction verifyTransaction = appUtil.getMapper().convertValue(apiResponse.getData(), VerifyTransaction.class);


        //Verify that the transaction reference matches the one you generated
        Transaction transaction = transactionRepository.findByTxRef(verifyTransaction.getTx_ref())
                .orElseThrow(() -> new RuntimeException("Invalid Transasction reference"));

        //Verify that the status of the transaction is successful
        if (verifyTransaction.getStatus().equalsIgnoreCase(TransactionStatus.SUCCESSFUL.name())) {
            transaction.setStatus(TransactionStatus.SUCCESSFUL.name());
        }
        //Verify that the currency of the payment was the expected currency
        else if (!verifyTransaction.getCurrency().equalsIgnoreCase(transaction.getCurrency())) {
            throw new RuntimeException("Error: currency mismatch");
        }

        // Update transaction amount and wallet with the actual amount paid.
        transaction.setAmount(verifyTransaction.getAmount());

        transaction.setStatus(transaction.getStatus().equalsIgnoreCase(TransactionStatus.PENDING.name()) ?
                TransactionStatus.FAILED.name() : TransactionStatus.SUCCESSFUL.name());

        //Update transaction history
        transactionRepository.save(transaction);

        //Update wallet
        System.out.println("Update wallet");

        return ApiResponse.<VerifyTransaction>builder()
                .data(verifyTransaction)
                .message(apiResponse.getMessage())
                .status(apiResponse.getStatus()).build();
    }
}
