package org.conway.dockertest.service;

import org.conway.dockertest.domain.AccountBill;
import org.conway.dockertest.mapper.CustomerAccountBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAccountBillService {

    @Autowired
    private CustomerAccountBillMapper accountBillMapper;

    public List<AccountBill> findBillsByCustomerId(long customerId) {
        return accountBillMapper.findBillsByCustomerId(customerId);
    }
}
