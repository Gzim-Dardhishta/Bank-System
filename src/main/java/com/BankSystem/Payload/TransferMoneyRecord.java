package com.BankSystem.Payload;

public record TransferMoneyRecord(
        double amount,
        int toAccount
) {
}
