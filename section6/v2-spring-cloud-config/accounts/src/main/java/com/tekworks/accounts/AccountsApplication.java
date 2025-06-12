package com.tekworks.accounts;

import com.tekworks.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.Builder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservice REST API Documentation",
				description = "Bank Accounts Microservice REST API Documentation",
				version = "1.0.0",
				contact = @Contact(
						name = "Ashok Gunji",
						email = "gunjiashok107@gmail.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)


		)
)
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {
		AccountsContactInfoDto.class})
public class AccountsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(AccountsApplication.class, args);
	}

}
