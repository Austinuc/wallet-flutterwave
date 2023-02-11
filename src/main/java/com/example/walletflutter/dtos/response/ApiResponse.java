package com.example.walletflutter.dtos.response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class ApiResponse<T> {

    private String status;
    private String message;
    private T data;
}
