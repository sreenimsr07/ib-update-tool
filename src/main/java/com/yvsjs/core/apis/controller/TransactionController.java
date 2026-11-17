package com.yvsjs.core.apis.controller;



import com.yvsjs.core.apis.requests.FundTransferRequest;
import com.yvsjs.core.apis.requests.UtilityPaymentRequest;
import com.yvsjs.core.apis.response.FundTransferResponse;
import com.yvsjs.core.apis.response.UtilityPaymentResponse;
import com.yvsjs.core.apis.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/fund-transfer")
    public ResponseEntity<FundTransferResponse> fundTransfer(@RequestBody FundTransferRequest fundTransferRequest) {
        log.info("Fund transfer initiated in core bank from{}",fundTransferRequest.toString());
        return ResponseEntity.ok(transactionService.fundTransfer(fundTransferRequest));
    }

    @PostMapping("/util-payment")
    public ResponseEntity<UtilityPaymentResponse> utilPayment(@RequestBody UtilityPaymentRequest utilityPaymentRequest)  {
        log.info("Utility Payment initiated in core bank from {}",utilityPaymentRequest.toString());
        return
                ResponseEntity.ok(transactionService.utilPayment(utilityPaymentRequest));
    }

}
