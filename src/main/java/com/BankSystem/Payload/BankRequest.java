package com.BankSystem.Payload;

public record BankRequest(
        String bankName,
        int transactionFlatFee,
        int transactionPercentFee
){
}
