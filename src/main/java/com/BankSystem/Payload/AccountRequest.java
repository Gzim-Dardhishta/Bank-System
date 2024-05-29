package com.BankSystem.Payload;

public record AccountRequest(
        String username,
        String bankName
) {
}
