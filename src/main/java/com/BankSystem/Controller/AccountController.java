package com.BankSystem.Controller;

import com.BankSystem.Entity.Account;
import com.BankSystem.Entity.Transaction;
import com.BankSystem.Payload.AccountRequest;
import com.BankSystem.Payload.TransferMoneyRecord;
import com.BankSystem.Payload.response.MessageResponse;
import com.BankSystem.Service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccount();
    }

    @GetMapping("account/{accountId}")
    public Account getAccount(@PathVariable int accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping("account/{accountId}/transaction")
    public List<Transaction> getTransactionOfAccount(@PathVariable int accountId) {
        return accountService.getTransactionFromAccount(accountId);
    }

    @PostMapping("add-account")
    public ResponseEntity<?> createNewAccount(@RequestBody AccountRequest accountRequest) {
        accountService.createAccount(accountRequest);
        return new ResponseEntity<>(
                new MessageResponse("Account created successfully"), HttpStatus.CREATED
        );
    }

    @PostMapping("/account/{accountId}/deposit")
    public ResponseEntity<?> depositMoneyToAccount(
            @RequestBody TransferMoneyRecord transfer,
            @PathVariable int accountId
    ) {
        accountService.depositMoney(accountId, transfer);
        return new ResponseEntity<>(
                new MessageResponse("Deposit of " + transfer.amount() + " was successful"), HttpStatus.CREATED
        );
    }

    @PostMapping("/account/{accountId}/send-money")
    public ResponseEntity<?> sendMoneyToAnotherAccount(
            @RequestBody TransferMoneyRecord transfer,
            @PathVariable int accountId
    ) {
        accountService.transferMoney(accountId, transfer);
        return new ResponseEntity<>(
                new MessageResponse("Transfer of " + transfer.amount() + " was successful"), HttpStatus.CREATED
        );
    }
}
