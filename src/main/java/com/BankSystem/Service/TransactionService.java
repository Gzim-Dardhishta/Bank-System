package com.BankSystem.Service;

import com.BankSystem.Entity.Account;
import com.BankSystem.Entity.Bank;
import com.BankSystem.Entity.Transaction;
import com.BankSystem.Exceptions.RequestValidationException;
import com.BankSystem.Exceptions.ResourceNotFoundException;
import com.BankSystem.Payload.TransferMoneyRecord;
import com.BankSystem.Repository.AccountRepository;
import com.BankSystem.Repository.BankRepository;
import com.BankSystem.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, AccountRepository accountRepository, BankRepository bankRepository) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    public Transaction getTransaction(int transactionId) {
        return transactionRepository
                .findById(transactionId).orElseThrow(
                () -> new ResourceNotFoundException("No transaction with id %s found".formatted(transactionId))
        );
    }
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }
}
