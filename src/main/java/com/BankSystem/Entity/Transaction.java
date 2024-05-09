package com.BankSystem.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double amount;
    private int originatingAccountId;
    private int resultingAccountId;
    private String reason;

    public Transaction() {
    }

    public Transaction(int id, double amount, int originatingAccountId, int resultingAccountId, String reason) {
        this.id = id;
        this.amount = amount;
        this.originatingAccountId = originatingAccountId;
        this.resultingAccountId = resultingAccountId;
        this.reason = reason;
    }

    public Transaction(double amount, int originatingAccountId, int resultingAccountId, String reason) {
        this.amount = amount;
        this.originatingAccountId = originatingAccountId;
        this.resultingAccountId = resultingAccountId;
        this.reason = reason;
    }
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getOriginatingAccountId() {
        return originatingAccountId;
    }

    public int getResultingAccountId() {
        return resultingAccountId;
    }

    public String getReason() {
        return reason;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setOriginatingAccountId(int originatingAccountId) {
        this.originatingAccountId = originatingAccountId;
    }

    public void setResultingAccountId(int resultingAccountId) {
        this.resultingAccountId = resultingAccountId;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", originatingAccountId=" + originatingAccountId +
                ", resultingAccountId=" + resultingAccountId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
