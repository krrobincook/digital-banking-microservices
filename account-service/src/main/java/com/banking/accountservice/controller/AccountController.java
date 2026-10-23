package com.banking.accountservice.controller;
import com.banking.accountservice.DTO.AccountResponse;
import com.banking.accountservice.DTO.CreateAccountRequest;
import com.banking.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount
            (@Valid @RequestBody CreateAccountRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(request));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount
            (@PathVariable String accountNumber){
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<String> getBalance
            (@PathVariable String accountNumber){
        return ResponseEntity.ok(accountService.getBalance(accountNumber));
    }

    @PutMapping("{accountNumber}/block")
    public ResponseEntity<String> blockAccount
            (@PathVariable String accountNumber){
        accountService.blockAccount(accountNumber);
        return ResponseEntity.ok("Account blocked successfully");
    }

    /**
     * SAGA step 1 - Deduct balance
     * called by transaction service when transfer is initiated
     */
    @PutMapping("/{accountNumber}/deduct")
    public ResponseEntity<String> deductBalance(@PathVariable String accountNumber, @RequestParam BigDecimal amount){
        accountService.deductBalance(accountNumber, amount);
        return ResponseEntity.ok("Balance deducted Successfully");
    }


    /** compensating transaction endpoint
     * called by transaction service in two scenarios
     * 1. Fraud detect - refund sender (undo step 1)
     * 2. Transaction complete - credit receiver
     */

    @PutMapping("/{accountNumber}/credit")
    public ResponseEntity<String> creditBalance(@PathVariable String accountNumber, @RequestParam BigDecimal amount){
        accountService.creditBalance(accountNumber, amount);
        return ResponseEntity.ok("BALANCE CREDITED SUCCESSFULLY");
    }
}