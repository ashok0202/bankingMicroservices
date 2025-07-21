package com.tekworks.accounts.config;


import com.tekworks.accounts.external.decoder.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeginConfig {


    @Bean
    ErrorDecoder errorDecoder() {
       return  new CustomErrorDecoder();
    }
}
