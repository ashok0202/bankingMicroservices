package com.tekworks.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;


@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/dochaybank/accounts/**")
                        .filters(f -> f.rewritePath("/dochaybank/accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config
                                        .setName("accountsCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/dochaybank/loans/**")
                        .filters(f -> f.rewritePath("/dochaybank/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .retry(retryConfig -> retryConfig.setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(1000),Duration.ofMillis(1000),2,true)))
                        .uri("lb://LOANS"))
                .route(p -> p
                        .path("/dochaybank/cards/**")
                        .filters(f -> f.rewritePath("/dochaybank/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://CARDS")).build();


    }

}
