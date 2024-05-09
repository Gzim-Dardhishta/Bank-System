package com.BankSystem.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bankName;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();
    private double totalTransactionFee;
    private double totalTransferAmount;
    private double transactionFlatFee;
    private double transactionPercentFee;

    public Bank(int id, String bankName, double transactionFlatFee, double transactionPercentFee, List<Account> accounts) {
        this.bankName = bankName;
        this.accounts = accounts;
        this.totalTransactionFee = 0;
        this.totalTransferAmount = 0;
        this.transactionFlatFee = transactionFlatFee;
        this.transactionPercentFee = transactionPercentFee;
    }

    public Bank() {}

    public int getId() {
        return id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public double getTotalTransactionFee() {
        return totalTransactionFee;
    }

    public void setTotalTransactionFee(double totalTransactionFee) {
        this.totalTransactionFee = totalTransactionFee;
    }

    public double getTotalTransferAmount() {
        return totalTransferAmount;
    }

    public void setTotalTransferAmount(double totalTransferAmount) {
        this.totalTransferAmount = totalTransferAmount;
    }

    public double getTransactionFlatFee() {
        return transactionFlatFee;
    }

    public void setTransactionFlatFee(double transactionFlatFee) {
        this.transactionFlatFee = transactionFlatFee;
    }

    public double getTransactionPercentFee() {
        return transactionPercentFee;
    }

    public void setTransactionPercentFee(double transactionPercentFee) {
        this.transactionPercentFee = transactionPercentFee;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", bankName='" + bankName + '\'' +
                ", accounts=" + accounts +
                ", totalTransactionFee=" + totalTransactionFee +
                ", totalTransferAmount=" + totalTransferAmount +
                ", transactionFlatFee=" + transactionFlatFee +
                ", transactionPercentFee=" + transactionPercentFee +
                '}';
    }
}
