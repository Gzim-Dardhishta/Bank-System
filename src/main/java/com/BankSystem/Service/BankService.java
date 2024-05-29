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
        if (bankRequest.bankName() == null) {
            throw new RequestValidationException("Bank name is required");
        }
        if(bankRequest.transactionFlatFee() < 1) {
            throw new RequestValidationException("Transaction Flat Fee is required and must be more than 5");
        }
        if (bankRequest.transactionPercentFee() < 1) {
            throw new RequestValidationException("Transaction Percent Fee is required  and must be between 1 and 8");
        }
        bank.setBankName(bankRequest.bankName());
        bank.setTransactionFlatFee(bankRequest.transactionFlatFee());
        bank.setTransactionPercentFee(bankRequest.transactionPercentFee());
        bankRepository.save(bank);
    }

    public double getTotalTransactionFeeAmount(int bankId) {
        Bank bank = getBank(bankId);

        return bank.getTotalTransactionFee();
    }

    public double getTotalTransferAmount(int bankId) {
        Bank bank = getBank(bankId);

        return bank.getTotalTransferAmount();
    }
}
