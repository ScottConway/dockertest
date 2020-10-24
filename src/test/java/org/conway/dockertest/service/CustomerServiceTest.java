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
}
