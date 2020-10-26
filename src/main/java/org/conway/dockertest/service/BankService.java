package org.conway.dockertest.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import org.conway.dockertest.domain.Bank;
import org.conway.dockertest.mapper.BankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankMapper bankMapper;


    public void insertBank(Bank bank) {
        bankMapper.insertBank(bank);
    }

    public Bank findBankById(long bankId) {
        return bankMapper.findBankById(bankId);
    }

    public void deleteBank(long bankId) {
        bankMapper.deleteBank(bankId);
    }

    public void upload(InputStream inputStream) {
        InputStreamReader reader = new InputStreamReader(inputStream);

        MappingStrategy<Bank> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(Bank.class);

        CsvToBeanBuilder<Bank> builder = new CsvToBeanBuilder<>(reader);
        CsvToBean<Bank> banks = builder.withMappingStrategy(mappingStrategy).build();

        banks.forEach(bank -> insertBank(bank));
    }

    public List<Bank> findAll() {
        return bankMapper.findAll();
    }
}
