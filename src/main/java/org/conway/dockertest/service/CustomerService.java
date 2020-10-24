package org.conway.dockertest.service;

import org.conway.dockertest.domain.Customer;
import org.conway.dockertest.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;


    public void insertCustomer(Customer customer) {
        customerMapper.insertCustomer(customer);
    }

    public Customer findCustomerById(long customerId) {
        return customerMapper.findCustomerById(customerId);
    }

    public void deleteCustomer(Customer customer) {
        customerMapper.deleteCustomer(customer);
    }
}
