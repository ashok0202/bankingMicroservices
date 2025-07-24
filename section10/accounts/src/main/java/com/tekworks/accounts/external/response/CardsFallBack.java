package com.tekworks.accounts.external.response;

import com.tekworks.accounts.dto.CardsDto;
import com.tekworks.accounts.service.client.CardsFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class CardsFallBack implements CardsFeignClient {
    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
