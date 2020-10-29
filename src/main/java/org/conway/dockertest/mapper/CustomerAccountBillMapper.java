package org.conway.dockertest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.conway.dockertest.domain.AccountBill;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerAccountBillMapper {
    List<AccountBill> findBillsByCustomerId(long customerId);
}
