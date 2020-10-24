package org.conway.dockertest.service;

import org.conway.dockertest.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("development")
public class CustomerServiceTest {
    private static final Customer CUSTOMER = new Customer(1, "Business Name");
    private static final Customer CUSTOMER2 = new Customer(2, "Another Business");
    private static final Long BAD_ID = 8675309L;

    private static final String UPLOAD = String.format("Customer Id,Customer Name\n%d,%s\n%d,%s",
            CUSTOMER.getCustomerId(), CUSTOMER.getName(), CUSTOMER2.getCustomerId(), CUSTOMER2.getName());

    @Autowired
    private CustomerService customerService;

    @DisplayName("Try and find a non-existent customer will return null.")
    @Test
    public void unableToFindCustomer() {
        assertNull(customerService.findCustomerById(BAD_ID));
    }

    @DisplayName("Nothing will happen when you delete a non-existent customer.")
    @Test
    public void deleteNonExistentCustomer() {
        Customer customer = new Customer(BAD_ID, "this business is not in the database.");
        //prove again the customer is not in the database
        assertNull(customerService.findCustomerById(customer.getCustomerId()));
        customerService.deleteCustomer(customer);
    }

    @DisplayName("Insert a customer and show that it is in the database by doing an select.")
    @Test
    public void insertCustomer() {
        customerService.insertCustomer(CUSTOMER);
        Customer foundCustomer = customerService.findCustomerById(CUSTOMER.getCustomerId());
        assertEquals(CUSTOMER, foundCustomer);
        customerService.deleteCustomer(foundCustomer);
        assertNull(customerService.findCustomerById(foundCustomer.getCustomerId()));
    }

    @DisplayName("Upload a file from a stream.")
    @Test
    public void upload() {
        assertNull(customerService.findCustomerById(CUSTOMER.getCustomerId()));
        assertNull(customerService.findCustomerById(CUSTOMER2.getCustomerId()));
        InputStream inputStream = new ByteArrayInputStream(UPLOAD.getBytes(Charset.forName("UTF-8")));

        customerService.upload(inputStream);

        assertEquals(CUSTOMER, customerService.findCustomerById(CUSTOMER.getCustomerId()));
        assertEquals(CUSTOMER2, customerService.findCustomerById(CUSTOMER2.getCustomerId()));

        customerService.deleteCustomer(CUSTOMER);
        customerService.deleteCustomer(CUSTOMER2);

        assertNull(customerService.findCustomerById(CUSTOMER.getCustomerId()));
        assertNull(customerService.findCustomerById(CUSTOMER2.getCustomerId()));
    }
}
