package com.BankSystem.Entity.DTO;

public record AccountDTO(
        int accountId,
        String username,
        double balance
) {
}
