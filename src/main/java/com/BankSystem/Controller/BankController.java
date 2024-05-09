package com.BankSystem.Controller;

import com.BankSystem.Entity.Bank;
import com.BankSystem.Service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banks")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public List<Bank> getBanks() {
        return bankService.getAllBanks();
    }

    @PostMapping("add")
    public ResponseEntity<?> addBank(
            @RequestBody BankService.BankRequest bankRequest
            ) {
        bankService.createBank(bankRequest);

        return new ResponseEntity<>(
                "Bank successfully", HttpStatus.CREATED
        );
    }
}
