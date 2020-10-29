package org.conway.dockertest.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import org.conway.dockertest.domain.CustomerAccount;
import org.conway.dockertest.mapper.CustomerAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CustomerAccountService {

    @Autowired
    private CustomerAccountMapper customerAccountMapper;

    public void insertCustomerAccount(CustomerAccount customerAccount) {
        customerAccountMapper.insertCustomerAccount(customerAccount);
    }

    public CustomerAccount findCustomerAccountById(long customerAccountId) {
        return customerAccountMapper.findCustomerAccountById(customerAccountId);
    }

    public void deleteCustomerAccount(long customerAccountId) {
        customerAccountMapper.deleteCustomerAccount(customerAccountId);
    }

    public void upload(InputStream inputStream) {
        InputStreamReader reader = new InputStreamReader(inputStream);

        MappingStrategy<CustomerAccount> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(CustomerAccount.class);

        CsvToBeanBuilder<CustomerAccount> builder = new CsvToBeanBuilder<>(reader);
        CsvToBean<CustomerAccount> customerAccounts = builder.withMappingStrategy(mappingStrategy).build();

        customerAccounts.forEach(customerAccount -> insertCustomerAccount(customerAccount));
    }

    public List<CustomerAccount> findAll() {
        return customerAccountMapper.findAll();
    }

    public List<CustomerAccount> findCustomerAccountByCustomerId(long customerId) {
        return customerAccountMapper.findCustomerAccountByCustomerId(customerId);
    }
}
