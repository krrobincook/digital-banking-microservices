package com.banking.accountservice.service;

import com.banking.accountservice.DTO.AccountResponse;
import com.banking.accountservice.DTO.CreateAccountRequest;
import com.banking.accountservice.entity.Account;
import com.banking.accountservice.entity.AccountStatus;
import com.banking.accountservice.entity.AccountType;
import com.banking.accountservice.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private static SecureRandom secureRandom = new SecureRandom();

    public AccountResponse createAccount(CreateAccountRequest request){
        log.info("Creating account for: {}", request.getEmail());

        if(accountRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Account already exists for email: "+ request.getEmail());
        }

        Account account = new Account();
        account.setAccountHolderName(request.getAccountHolderName());
        account.setEmail(request.getEmail());
        account.setPhone(request.getPhone());
        account.setAccountType(request.getAccountType());
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(request.getInitialDeposit());
        account.setAccountNumber(generateAccountNumber());
        account.setDailyTransactionLimit(
                request.getAccountType() == AccountType.SAVINGS
                ? new BigDecimal("100000") : new BigDecimal("50000")
        );
        Account savedAccount = accountRepository.save(account);
        log.info("Account created: {}", savedAccount.getAccountNumber());
        return mapToResponse(savedAccount);
    }

    private String generateAccountNumber(){
        String accountNumber;
        do{
            long number = secureRandom.nextLong(1_000_000_000_000L);
            accountNumber = String.format("%012d", number);
        } while(accountRepository.existsByAccountNumber(accountNumber)){
            return accountNumber;
        }
    }
    private AccountResponse mapToResponse(Account account){
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountType(account.getAccountType());
        response.setAccountHolderName(account.getAccountHolderName());
        response.setEmail(account.getEmail());
        response.setPhone(account.getPhone());
        response.setStatus(account.getStatus());
        response.setBalance(account.getBalance());
        response.setDailyTransactionLimit(account.getDailyTransactionLimit());
        response.setCreatedAt(account.getCreatedAt());
        return response;
    }
}
