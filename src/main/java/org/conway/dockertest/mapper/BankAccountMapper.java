package org.conway.dockertest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.conway.dockertest.domain.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BankAccountMapper {
    void insertBankAccount(BankAccount bankAccount);

    void deleteBankAccount(long bankAccountId);

    BankAccount findBankAccountById(long bankAccountId);

    List<BankAccount> findAll();
}
