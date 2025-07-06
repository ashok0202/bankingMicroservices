package com.tekworks.accounts.service.impl;

import com.tekworks.accounts.dto.AccountsDto;
import com.tekworks.accounts.dto.CardsDto;
import com.tekworks.accounts.dto.CustomerDetailsDto;
import com.tekworks.accounts.dto.LoansDto;
import com.tekworks.accounts.entity.Accounts;
import com.tekworks.accounts.entity.Customer;
import com.tekworks.accounts.exception.ResourceNotFoundException;
import com.tekworks.accounts.mapper.AccountMapper;
import com.tekworks.accounts.mapper.CustomerMapper;
import com.tekworks.accounts.repository.AccountsRepository;
import com.tekworks.accounts.repository.CustomerRepository;
import com.tekworks.accounts.service.CustomersService;
import com.tekworks.accounts.service.client.CardsFeignClient;
import com.tekworks.accounts.service.client.LoansFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomersServiceImpl implements CustomersService {

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CardsFeignClient cardsFeignClient;
    @Autowired
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId() + "")
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}