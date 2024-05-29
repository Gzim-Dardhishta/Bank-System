package com.BankSystem.Service;

import com.BankSystem.Entity.Account;
import com.BankSystem.Entity.Bank;
import com.BankSystem.Entity.DTO.AccountDTO;
import com.BankSystem.Entity.DTO.AccountDTOMapper;
import com.BankSystem.Entity.Transaction;
import com.BankSystem.Exceptions.RequestValidationException;
import com.BankSystem.Exceptions.ResourceNotFoundException;
import com.BankSystem.Payload.AccountRequest;
import com.BankSystem.Payload.TransferMoneyRecord;
import com.BankSystem.Repository.AccountRepository;
import com.BankSystem.Repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;

    public AccountService(AccountRepository accountRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    public Account getAccount(int accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No Account with id %s fount!".formatted(accountId))
                );
    }

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    public List<Transaction> getTransactionFromAccount(int accountId) {
        Account account = getAccount(accountId);

        return account.getTransactions();
    }

    public void createAccount(AccountRequest accountRequest) {
        if (accountRequest.username() == null) {
            throw new RequestValidationException("Username for account should not be empty");
        }
        if (accountRequest.bankName() == null) {
            throw new RequestValidationException("BankName should not be empty");
        }
        Bank bank = bankRepository.findByBankName(accountRequest.bankName());
        Account account = new Account();
        account.setUsername(accountRequest.username());
        account.setBank(bank);
        bank.addAccount(account);

        accountRepository.save(account);
    }

    public void depositMoney(int accountId, TransferMoneyRecord transfer) {
        Account account = getAccount(accountId);

        account.deposit(transfer.amount());
        account.getBank().updateTotalTransferAmount(transfer.amount());

        accountRepository.save(account);
    }

    public void transferMoney(int fromAccountId, TransferMoneyRecord transfer) {
        Account fromAccount = getAccount(fromAccountId);
        Account toAccount = getAccount(transfer.toAccount());

        if (!fromAccount.getBank().equals(toAccount.getBank())) {
            throw new RequestValidationException("Inter-bank transfers not supported yet.");
        }

        double fee = calculateFee(fromAccount.getBank(), transfer.amount());

        if (fromAccount.getAccountBalance() < transfer.amount() + fee) {
            throw new RequestValidationException("Insufficient funds in account.");
        }

        fromAccount.withdraw(transfer.amount() + fee);
        toAccount.deposit(transfer.amount());

        fromAccount.getBank().updateTotalTransactionFee(fee);
        fromAccount.getBank().updateTotalTransferAmount(transfer.amount());

        fromAccount.addTransaction(new Transaction(transfer.amount() + fee, fromAccountId, transfer.toAccount(), "Transfer"));
        toAccount.addTransaction(new Transaction(transfer.amount(), transfer.toAccount(), fromAccountId, "Transfer (Deposit)"));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        bankRepository.save(fromAccount.getBank());
    }

    private double calculateFee(Bank bank, double amount) {
        if (bank.getTransactionFlatFee() > 0) {
            return bank.getTransactionFlatFee();
        } else {
            return amount * bank.getTransactionPercentFee() / 100;
        }
    }
}
