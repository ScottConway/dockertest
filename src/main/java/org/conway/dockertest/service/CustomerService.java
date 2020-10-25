package org.conway.dockertest.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import org.conway.dockertest.domain.Customer;
import org.conway.dockertest.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;

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

    public void deleteCustomer(long customerId) {
        customerMapper.deleteCustomer(customerId);
    }

    public void upload(InputStream inputStream) {
        InputStreamReader reader = new InputStreamReader(inputStream);

        MappingStrategy<Customer> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(Customer.class);

        CsvToBeanBuilder<Customer> builder = new CsvToBeanBuilder<>(reader);
        CsvToBean<Customer> customers = builder.withMappingStrategy(mappingStrategy).build();

        customers.forEach(customer -> insertCustomer(customer));
    }
}
