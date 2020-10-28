package org.conway.dockertest.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import org.conway.dockertest.domain.CustomerBill;
import org.conway.dockertest.mapper.CustomerBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CustomerBillService {

    @Autowired
    private CustomerBillMapper customerBillMapper;

    public void insertCustomerBill(CustomerBill customerBill) {
        customerBillMapper.insertCustomerBill(customerBill);
    }

    public CustomerBill findCustomerBillById(long customerBillId) {
        return customerBillMapper.findCustomerBillById(customerBillId);
    }

    public void deleteCustomerBill(long customerBillId) {
        customerBillMapper.deleteCustomerBill(customerBillId);
    }

    public void upload(InputStream inputStream) {
        InputStreamReader reader = new InputStreamReader(inputStream);

        MappingStrategy<CustomerBill> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(CustomerBill.class);

        CsvToBeanBuilder<CustomerBill> builder = new CsvToBeanBuilder<>(reader);
        CsvToBean<CustomerBill> customerBills = builder.withMappingStrategy(mappingStrategy).build();

        customerBills.forEach(customerBill -> insertCustomerBill(customerBill));
    }

    public List<CustomerBill> findAll() {
        return customerBillMapper.findAll();
    }
}
