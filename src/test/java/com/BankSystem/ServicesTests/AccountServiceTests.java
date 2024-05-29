package com.BankSystem.ServicesTests;
import com.BankSystem.Entity.Account;
import com.BankSystem.Entity.Bank;
import com.BankSystem.Entity.Transaction;
import com.BankSystem.Payload.AccountRequest;
import com.BankSystem.Payload.TransferMoneyRecord;
import com.BankSystem.Repository.AccountRepository;
import com.BankSystem.Repository.BankRepository;
import com.BankSystem.Service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BankRepository bankRepository;

    @Test
    public void testGetAccount() {
        // Given
        int accountId = 1;
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // When
        Account result = accountService.getAccount(accountId);

        // Then
        assertEquals(account, result);
    }

    @Test
    public void testGetAllAccount() {
        // Given
        List<Account> accounts = List.of(new Account(), new Account());
        when(accountRepository.findAll()).thenReturn(accounts);

        // When
        List<Account> result = accountService.getAllAccount();

        // Then
        assertEquals(accounts, result);
    }

    @Test
    public void testGetTransactionFromAccount() {
        // Given
        int accountId = 1;
        Account account = new Account();
        when(accountService.getAccount(accountId)).thenReturn(account);

        // When
        List<Transaction> result = accountService.getTransactionFromAccount(accountId);

        // Then
        assertEquals(account.getTransactions(), result);
    }

    @Test
    public void testCreateAccount() {
        // Given
        AccountRequest accountRequest = new AccountRequest("username", "bankName");
        when(bankRepository.findByBankName(accountRequest.bankName())).thenReturn(new Bank());

        // When
        accountService.createAccount(accountRequest);

        // Then
        // No assertion needed, as the method is void
    }

    @Test
    public void testDepositMoney() {
        // Given
        int accountId = 1;
        Account account = new Account();
        when(accountService.getAccount(accountId)).thenReturn(account);

        // When
        accountService.depositMoney(accountId, new TransferMoneyRecord(100, accountId));

        // Then
        assertEquals(100, account.getAccountBalance());
    }

//    @Test
//    public void testTransferMoney() {
//        // Given
//        int fromAccountId = 1;
//        int toAccountId = 2;
//        Account fromAccount = new Account();
//        Account toAccount = new Account();
//        when(accountService.getAccount(fromAccountId)).thenReturn(fromAccount);
//        when(accountService.getAccount(toAccountId)).thenReturn(toAccount);
//
//        // When
//        accountService.transferMoney(fromAccountId, new TransferMoneyRecord(100));
//
//        // Then
//        assertEquals(100, fromAccount.getAccountBalance());
//        assertEquals(100, toAccount.getAccountBalance());
//    }

//    @Test
//    public void testTransferMoneyInsufficientFunds() {
//        // Given
//        int fromAccountId = 1;
//        Account fromAccount = new Account();
//        when(accountService.getAccount(fromAccountId)).thenReturn(fromAccount);
//
//        // When
//        assertThrows(RequestValidationException.class, () -> accountService.transferMoney(fromAccountId, new TransferMoneyRecord(100)));
//
//        // Then
//        // No assertion needed, as the method throws an exception
//    }
}