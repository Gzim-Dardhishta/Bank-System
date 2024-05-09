package com.BankSystem.Entity;

import com.BankSystem.Exceptions.RequestValidationException;

public interface AccountOperations {
    void deposit(double amount) throws RequestValidationException;

    void withdraw(double amount) throws RequestValidationException;
}
