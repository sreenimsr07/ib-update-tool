package com.yvsjs.core.apis.service.impl;

import com.yvsjs.core.apis.dto.BankAccount;
import com.yvsjs.core.apis.dto.UtilityAccount;
import com.yvsjs.core.apis.entity.BankAccountEntity;
import com.yvsjs.core.apis.entity.UtilityAccountEntity;
import com.yvsjs.core.apis.exception.RecordNotFoundException;
import com.yvsjs.core.apis.mapper.BankAccountMapper;
import com.yvsjs.core.apis.mapper.UtilityAccountMapper;
import com.yvsjs.core.apis.repository.BankAccountRepository;
import com.yvsjs.core.apis.repository.UtilityAccountRepository;
import com.yvsjs.core.apis.requests.AccountCreationRequest;
import com.yvsjs.core.apis.requests.NotifyRequest;
import com.yvsjs.core.apis.response.AccountCreationResponse;
import com.yvsjs.core.apis.response.NotifyResponse;
import com.yvsjs.core.apis.service.AccountService;
import com.yvsjs.core.apis.types.AccountStatus;
import com.yvsjs.core.apis.types.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final BankAccountMapper bankAccountMapper;
    private final UtilityAccountMapper utilityAccountMapper;
    private final BankAccountRepository bankAccountRepository;
    private final UtilityAccountRepository utilityAccountRepository;

   private final RestTemplate restTemplate;
    @Value("${notificationServiceUrl}")
    private String url;

    @Override
    public AccountCreationResponse createAccount(AccountCreationRequest accountCreationRequest) {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();

        bankAccountEntity.setNumber(accountCreationRequest.getNumber());
        bankAccountEntity.setUser(accountCreationRequest.getUser());
        BigDecimal depositedAmount = deposit();
        bankAccountEntity.setActualBalance(depositedAmount);
        bankAccountEntity.setAvailableBalance(depositedAmount);
        bankAccountEntity.setStatus(AccountStatus.ACTIVE);
        bankAccountEntity.setType(AccountType.SAVINGS_ACCOUNT);

        bankAccountRepository.save(bankAccountEntity);

        AccountCreationResponse accountCreationResponse= new AccountCreationResponse();
        accountCreationResponse.setMessage("Account created for the user");
        accountCreationResponse.setStatusCode(200);
        NotifyResponse notifyResponse=doNotify(accountCreationRequest);
        return accountCreationResponse;
    }

    private NotifyResponse doNotify(AccountCreationRequest accountCreationRequest) {
        String notifyUrl = url + "/notify";

        NotifyRequest notifyRequest = new NotifyRequest();
        notifyRequest.setUserId(accountCreationRequest.getUser());
        notifyRequest.setMessage("Account created successfully for user: " + accountCreationRequest.getUser());

        return restTemplate.postForObject(notifyUrl, notifyRequest, NotifyResponse.class);
    }



    public BigDecimal deposit()
    {
        BigDecimal depositAmount = new BigDecimal("100.00");
        System.out.println("Money got Deposited");
        return  depositAmount;

    }


    @Override
    public BankAccount readBankAccount(String accountNumber) {
        BankAccountEntity entity = bankAccountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RecordNotFoundException("Bank account not found with number: " + accountNumber));
        return bankAccountMapper.convertToDto(entity);
    }

    @Override
    public UtilityAccount readUtilityAccount(String provider) {
        UtilityAccountEntity utilityAccountEntity = utilityAccountRepository.findByProviderName(provider)
                .orElseThrow(() -> new RecordNotFoundException("Utility Provider not found with name: " + provider));
        return utilityAccountMapper.convertToDto(utilityAccountEntity);
    }

    public UtilityAccount readUtilityAccount(Long id) {
        UtilityAccountEntity utilityAccountEntity = utilityAccountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("UtilityAccount not found with id: " + id));
        return utilityAccountMapper.convertToDto(utilityAccountEntity);
    }

}
