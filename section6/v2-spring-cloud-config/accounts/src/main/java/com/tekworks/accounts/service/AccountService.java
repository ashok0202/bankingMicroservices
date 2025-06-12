package com.tekworks.accounts.service;

import com.tekworks.accounts.dto.CustomerDto;
import org.springframework.stereotype.Service;


public interface  AccountService {

    /**
     * Creates an account
     * @param customerDto Information about the account to be created.
     */
    void createAccount(CustomerDto customerDto);

    /**
     * Retrieves the account details for a given mobile number.
     * @param mobileNumber The mobile number of the account.
     * @return The account details.
     */
    CustomerDto fetchAccountDetails(String mobileNumber);

    /**
     * Updates the account details
     * @param customerDto The updated account information.
     * @return True if the account is updated successfully.
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * Deletes an account
     * @param mobileNumber The mobile number of the account.
     * @return True if the account is deleted successfully.
     */
    boolean deleteAccount(String mobileNumber);
}
