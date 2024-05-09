package com.BankSystem.Service;

import com.BankSystem.Entity.Bank;
import com.BankSystem.Exceptions.RequestValidationException;
import com.BankSystem.Exceptions.ResourceNotFoundException;
import com.BankSystem.Payload.BankRequest;
import com.BankSystem.Repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank getBank(int bankId) {
        return bankRepository.findById(bankId).orElseThrow(() ->
                new ResourceNotFoundException("No bank with id %s found".formatted(bankId)));
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public void createBank(BankRequest bankRequest) {
        Bank bank = new Bank();
        if (bankRequest.bankName() == null || bankRequest.transactionFlatFee() < 1 || bankRequest.transactionPercentFee() < 1) {
            throw new RequestValidationException("All fields are required");
        }
        bank.setBankName(bankRequest.bankName());
        bank.setTransactionFlatFee(bankRequest.transactionFlatFee());
        bank.setTransactionPercentFee(bankRequest.transactionPercentFee());
        bankRepository.save(bank);
    }
}
