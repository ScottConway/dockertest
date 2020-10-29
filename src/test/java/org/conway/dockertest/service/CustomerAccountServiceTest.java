package org.conway.dockertest.service;

import org.conway.dockertest.domain.CustomerAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("development")
public class CustomerAccountServiceTest {
    private static final long CUSTOMER_ID1 = 1000;
    private static final long CUSTOMER_ID2 = 1001;
    private static final CustomerAccount ACCOUNT = new CustomerAccount(1, CUSTOMER_ID1, "Widget Supplier", "Widgets R US");
    private static final CustomerAccount ACCOUNT2 = new CustomerAccount(2, CUSTOMER_ID2, "Parts Supplier", "Widgets R US");
    private static final Long BAD_ID = 8675309L;

    private static final String UPLOAD = String.format("Customer Account Id, Customer Id,Account Name,Business Name\n%d,%d,%s,%s\n%d,%d,%s,%s",
            ACCOUNT.getCustomerAccountId(), ACCOUNT.getCustomerId(), ACCOUNT.getAccountName(), ACCOUNT.getBusinessName(),
            ACCOUNT2.getCustomerAccountId(), ACCOUNT2.getCustomerId(), ACCOUNT2.getAccountName(), ACCOUNT2.getBusinessName());

    @Autowired
    private CustomerAccountService customerService;

    @AfterEach
    public void clearTestCustomerAccountsFromDatabase() {
        customerService.deleteCustomerAccount(ACCOUNT.getCustomerAccountId());
        customerService.deleteCustomerAccount(ACCOUNT2.getCustomerAccountId());
    }

    @DisplayName("Try and find a non-existent customer will return null.")
    @Test
    public void unableToFindCustomerAccount() {
        assertNull(customerService.findCustomerAccountById(BAD_ID));
    }

    @DisplayName("Nothing will happen when you delete a non-existent customer.")
    @Test
    public void deleteNonExistentCustomerAccount() {
        CustomerAccount customer = new CustomerAccount(BAD_ID, CUSTOMER_ID1, "account does not exist", "business does not exist");
        //prove again the customer is not in the database
        assertNull(customerService.findCustomerAccountById(customer.getCustomerAccountId()));
        customerService.deleteCustomerAccount(customer.getCustomerAccountId());
    }

    @DisplayName("Insert a customer and show that it is in the database by doing an select.")
    @Test
    public void insertCustomerAccount() {
        customerService.insertCustomerAccount(ACCOUNT);
        CustomerAccount foundCustomerAccount = customerService.findCustomerAccountById(ACCOUNT.getCustomerAccountId());
        assertEquals(ACCOUNT, foundCustomerAccount);
        customerService.deleteCustomerAccount(foundCustomerAccount.getCustomerAccountId());
        assertNull(customerService.findCustomerAccountById(foundCustomerAccount.getCustomerAccountId()));
    }

    @DisplayName("Upload a file from a stream.")
    @Test
    public void upload() {
        assertNull(customerService.findCustomerAccountById(ACCOUNT.getCustomerAccountId()));
        assertNull(customerService.findCustomerAccountById(ACCOUNT2.getCustomerAccountId()));
        InputStream inputStream = new ByteArrayInputStream(UPLOAD.getBytes(Charset.forName("UTF-8")));

        customerService.upload(inputStream);

        assertEquals(ACCOUNT, customerService.findCustomerAccountById(ACCOUNT.getCustomerAccountId()));
        assertEquals(ACCOUNT2, customerService.findCustomerAccountById(ACCOUNT2.getCustomerAccountId()));

        customerService.deleteCustomerAccount(ACCOUNT.getCustomerAccountId());
        customerService.deleteCustomerAccount(ACCOUNT2.getCustomerAccountId());

        assertNull(customerService.findCustomerAccountById(ACCOUNT.getCustomerAccountId()));
        assertNull(customerService.findCustomerAccountById(ACCOUNT2.getCustomerAccountId()));
    }

    @DisplayName("Display all CustomerAccounts")
    @Test
    public void all() {
        assertNull(customerService.findCustomerAccountById(ACCOUNT.getCustomerAccountId()));
        assertNull(customerService.findCustomerAccountById(ACCOUNT2.getCustomerAccountId()));
        InputStream inputStream = new ByteArrayInputStream(UPLOAD.getBytes(Charset.forName("UTF-8")));

        customerService.upload(inputStream);

        List<CustomerAccount> customerList = customerService.findAll();
        assertNotNull(customerList);
        assertEquals(2, customerList.size());
        assertTrue(customerList.contains(ACCOUNT));
        assertTrue(customerList.contains(ACCOUNT2));

        customerService.deleteCustomerAccount(ACCOUNT.getCustomerAccountId());
        customerService.deleteCustomerAccount(ACCOUNT2.getCustomerAccountId());
        customerList = customerService.findAll();
        assertNotNull(customerList);
        assertTrue(customerList.isEmpty());
    }

    @DisplayName("find all accounts for a given customer will return empty list when there is no data.")
    @Test
    public void findAllCustomerAccountsByCustomerIdNoData() {
        assertTrue(customerService.findCustomerAccountByCustomerId(CUSTOMER_ID1).isEmpty());
    }

    @DisplayName("find all accounts for a given customer")
    @Test
    public void findAllCustomerAccountsByCustomerId() {
        assertTrue(customerService.findCustomerAccountByCustomerId(CUSTOMER_ID1).isEmpty());
        InputStream inputStream = new ByteArrayInputStream(UPLOAD.getBytes(Charset.forName("UTF-8")));

        customerService.upload(inputStream);

        List<CustomerAccount> customerAccounts1 = customerService.findCustomerAccountByCustomerId(CUSTOMER_ID1);
        List<CustomerAccount> customerAccounts2 = customerService.findCustomerAccountByCustomerId(CUSTOMER_ID2);

        assertEquals(1, customerAccounts1.size());
        assertEquals(1, customerAccounts2.size());
        assertEquals(ACCOUNT, customerAccounts1.get(0));
        assertEquals(ACCOUNT2, customerAccounts2.get(0));
    }
}
