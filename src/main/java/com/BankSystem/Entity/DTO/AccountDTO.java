package com.BankSystem.Entity.DTO;

import com.BankSystem.Entity.Bank;

public record AccountDTO(
        int accountId,
        String username,
        double balance,
        Bank bank
) {
}
