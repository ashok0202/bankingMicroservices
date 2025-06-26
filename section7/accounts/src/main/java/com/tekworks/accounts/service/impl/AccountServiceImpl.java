package com.tekworks.accounts.service.impl;

import com.tekworks.accounts.constants.AccountsContstants;
import com.tekworks.accounts.dto.AccountsDto;
import com.tekworks.accounts.dto.CustomerDto;
import com.tekworks.accounts.entity.Accounts;
import com.tekworks.accounts.entity.Customer;
import com.tekworks.accounts.exception.CustomerAlreadyExistsException;
import com.tekworks.accounts.exception.ResourceNotFoundException;
import com.tekworks.accounts.mapper.AccountMapper;
import com.tekworks.accounts.mapper.CustomerMapper;
import com.tekworks.accounts.repository.AccountsRepository;
import com.tekworks.accounts.repository.CustomerRepository;
import com.tekworks.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer> byMobileNumber = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(byMobileNumber.isPresent()){
            throw new CustomerAlreadyExistsException("Customer with mobile number "+customer.getMobileNumber()+" already exists");
        }


        customerRepository.save(customer);
        accountsRepository.save(createAccounts(customer));


    }

    /**
     * @param mobileNumber The mobile number of the account.
     * @return
     */
    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId() + "")
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto The updated account information.
     * @return
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null) {
            Accounts accounts=accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Accounts", "id", accountsDto.getAccountNumber() + "")
            );
            AccountMapper.mapToAccounts(accountsDto,accounts);
            accounts=accountsRepository.save(accounts);
            long customerId=accounts.getCustomerId();
            Customer customer=customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "id", customerId + "")
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }



    private Accounts createAccounts(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        long randomNumber = 1000000000L+new Random().nextInt(900000000);
        accounts.setAccountNumber(randomNumber);
        accounts.setAccountType(AccountsContstants.SAVINGS);
        accounts.setBranchAddress(AccountsContstants.ADDRESS);
        return accounts;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;


    }

}
