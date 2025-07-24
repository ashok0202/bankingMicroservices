package com.tekworks.accounts.external.response;

import com.tekworks.accounts.dto.LoansDto;
import com.tekworks.accounts.service.client.LoansFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class LoansFallback implements LoansFeignClient {
    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
