package org.conway.dockertest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.conway.dockertest.domain.CustomerAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerAccountMapper {
    void insertCustomerAccount(CustomerAccount customerAccount);

    void deleteCustomerAccount(long customerAccountId);

    CustomerAccount findCustomerAccountById(long customerAccountId);

    List<CustomerAccount> findCustomerAccountByCustomerId(long customerId);

    List<CustomerAccount> findAll();
}
