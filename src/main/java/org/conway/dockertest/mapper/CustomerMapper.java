package org.conway.dockertest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.conway.dockertest.domain.Customer;

@Mapper
public interface CustomerMapper {
    void insertCustomer(Customer customer);

    void deleteCustomer(long customerId);

    Customer findCustomerById(long customerId);
}
