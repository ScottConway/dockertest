package org.conway.dockertest.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import org.conway.dockertest.domain.BankAccount;
import org.conway.dockertest.mapper.BankAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountMapper bankAccountMapper;


    public void insertBankAccount(BankAccount bankAccount) {
        bankAccountMapper.insertBankAccount(bankAccount);
    }

    public BankAccount findBankAccountById(long bankAccountId) {
        return bankAccountMapper.findBankAccountById(bankAccountId);
    }

    public void deleteBankAccount(long bankAccountId) {
        bankAccountMapper.deleteBankAccount(bankAccountId);
    }

    public void upload(InputStream inputStream) {
        InputStreamReader reader = new InputStreamReader(inputStream);

        MappingStrategy<BankAccount> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(BankAccount.class);

        CsvToBeanBuilder<BankAccount> builder = new CsvToBeanBuilder<>(reader);
        CsvToBean<BankAccount> bankAccounts = builder.withMappingStrategy(mappingStrategy).build();

        bankAccounts.forEach(bankAccount -> insertBankAccount(bankAccount));
    }

    public List<BankAccount> findAll() {
        return bankAccountMapper.findAll();
    }
}
