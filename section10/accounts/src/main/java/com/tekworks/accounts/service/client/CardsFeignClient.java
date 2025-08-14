package com.tekworks.accounts.service.client;


import com.tekworks.accounts.dto.CardsDto;
import com.tekworks.accounts.external.response.CardsFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "cards",fallback = CardsFallBack.class)
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch",consumes = "application/json")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("bank-correlation-id") String correlationId, @RequestParam String mobileNumber);

}
