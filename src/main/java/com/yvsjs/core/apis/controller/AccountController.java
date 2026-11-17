package com.yvsjs.core.apis.controller;


import com.yvsjs.core.apis.dto.BankAccount;
import com.yvsjs.core.apis.dto.UtilityAccount;
import com.yvsjs.core.apis.requests.AccountCreationRequest;
import com.yvsjs.core.apis.response.AccountCreationResponse;
import com.yvsjs.core.apis.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account_creation")
    public AccountCreationResponse createAccount(@RequestBody AccountCreationRequest accountCreationRequest) {
        return accountService.createAccount(accountCreationRequest);

    }


    @GetMapping("/bank-account/{account_number}")
    public ResponseEntity<BankAccount> getBankAccount(@PathVariable("account_number") String accountNumber) {
        log.info("Reading account by ID {}", accountNumber);
        return ResponseEntity.ok(accountService.readBankAccount(accountNumber));
    }

    @GetMapping("/util-account/{account_name}")
    public ResponseEntity<UtilityAccount> getUtilityAccount(@PathVariable("account_name") String providerName) {
        log.info("Reading utility account by ID {}", providerName);
        return ResponseEntity.ok(accountService.readUtilityAccount(providerName));
    }
}
