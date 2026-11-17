package com.yvsjs.core.apis.requests;

import com.yvsjs.core.apis.entity.UserEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreationRequest {
    private String number;
    private BigDecimal availableBalance;
    private BigDecimal actualBalance;
    private UserEntity user;
}
