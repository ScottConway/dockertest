package org.conway.dockertest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.conway.dockertest.domain.BankAccount;

import java.util.List;

@Mapper
public interface BankAccountMapper {
    void insertBankAccount(BankAccount bankAccount);

    void deleteBankAccount(long bankAccountId);

    BankAccount findBankAccountById(long bankAccountId);

    List<BankAccount> findAll();
}
