package org.conway.dockertest.service;

import org.conway.dockertest.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("development")
public class CustomerServiceTest {
    private static final Customer CUSTOMER = new Customer(1, "Business Name.");
    private static final Long BAD_ID = 8675309L;
    @Autowired
    private CustomerService customerService;

    @DisplayName("Insert a customer and show that it is in the database by doing an select.")
    @Test
    public void insertCustomer() {
        customerService.insertCustomer(CUSTOMER);
        Customer foundCustomer = customerService.findCustomerById(CUSTOMER.getCustomerId());
        assertEquals(CUSTOMER, foundCustomer);
    }

    @DisplayName("Try and find a non-existant customer")
    @Test
    public void unableToFindCustomer() {
        assertNull(customerService.findCustomerById(BAD_ID));
    }
}
