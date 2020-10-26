package org.conway.dockertest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.conway.dockertest.domain.Bank;

import java.util.List;

@Mapper
public interface BankMapper {
    void insertBank(Bank bank);

    void deleteBank(long bankId);

    Bank findBankById(long bankId);

    List<Bank> findAll();
}
