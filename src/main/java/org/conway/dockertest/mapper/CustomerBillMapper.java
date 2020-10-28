package org.conway.dockertest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.conway.dockertest.domain.CustomerBill;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerBillMapper {
    void insertCustomerBill(CustomerBill customerBill);

    void deleteCustomerBill(long customerBillId);

    CustomerBill findCustomerBillById(long customerBillId);

    List<CustomerBill> findAll();
}
