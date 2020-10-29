package org.conway.dockertest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.conway.dockertest.domain.Bank;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BankMapper {
    void insertBank(Bank bank);

    void deleteBank(long bankId);

    Bank findBankById(long bankId);

    List<Bank> findAll();
}
