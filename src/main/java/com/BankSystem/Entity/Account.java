package com.BankSystem.Entity;

import com.BankSystem.Exceptions.RequestValidationException;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public class Account implements AccountOperations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;
    private String username;
    private double accountBalance;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Transaction> transactions;
    @ManyToOne
    private Bank bank;

    public Account() {
    }

    public Account(int accountId, String username, double accountBalance, List<Transaction> transactions) {
        this.accountId = accountId;
        this.username = username;
        this.accountBalance = accountBalance;
        this.transactions = transactions;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public void deposit(double amount) throws RequestValidationException {
        if (amount > 5000) {
            throw new RequestValidationException("You can not deposit more than 5000$ via ATM");
        }
        accountBalance += amount;
        var reason = "Deposit " + amount + "$ " + LocalDateTime.now();
        addTransaction(new Transaction(amount, 0, accountId, reason));
    }

    @Override
    public void withdraw(double amount) throws RequestValidationException {
        if (accountBalance < amount) {
            throw new RequestValidationException("Not enough money to withdraw! Check the Balance");
        }
        if (accountBalance - amount == 0) {
            throw new RequestValidationException("you can not withdraw more than you have in balance");
        }
        accountBalance -= amount;
        var reason = "Withdraw of " + amount + "$ " + LocalDateTime.now();
        addTransaction(new Transaction(amount, accountId, 0, reason));
    }

    protected void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
