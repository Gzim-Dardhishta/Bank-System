package com.BankSystem.Entity.DTO;

import com.BankSystem.Entity.Account;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountDTOMapper implements Function<Account, AccountDTO> {
    @Override
    public AccountDTO apply(Account acc) {
        return new AccountDTO(
                acc.getAccountId(),
                acc.getUsername(),
                acc.getAccountBalance()
        );
    }
}
