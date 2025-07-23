package com.tekworks.accounts.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tekworks.accounts.dto.ErrorResponseDto;
import com.tekworks.accounts.exception.CustomException;
import com.tekworks.accounts.exception.ResourceNotFoundException;
import com.tekworks.accounts.external.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class CustomErrorDecoder implements ErrorDecoder{

    private final static Logger logger = LoggerFactory.getLogger(CustomErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        logger.info("::{}",response.request().url());
        logger.info("::{}",response.request().headers());
        try {
            ErrorResponseDto errorResponseDto = objectMapper.readValue(response.body().asInputStream(), ErrorResponseDto.class);
            return new CustomException(errorResponseDto.getErrorMessage(), errorResponseDto.getMessage(), errorResponseDto.getErrorCode());

        } catch (IOException e) {
            throw new CustomException(e.getMessage(), "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
