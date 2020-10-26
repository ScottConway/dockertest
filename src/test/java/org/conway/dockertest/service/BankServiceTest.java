package org.conway.dockertest.service;

import org.conway.dockertest.domain.Bank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("development")
public class BankServiceTest {
    private static final Bank BANK = new Bank(1, "Business Name");
    private static final Bank BANK2 = new Bank(2, "Another Business");
    private static final Long BAD_ID = 8675309L;

    private static final String UPLOAD = String.format("Bank Id,Bank Name\n%d,%s\n%d,%s",
            BANK.getBankId(), BANK.getName(), BANK2.getBankId(), BANK2.getName());

    @Autowired
    private BankService bankService;

    @AfterEach
    public void clearTestBanksFromDatabase() {
        bankService.deleteBank(BANK.getBankId());
        bankService.deleteBank(BANK2.getBankId());
    }

    @DisplayName("Try and find a non-existent bank will return null.")
    @Test
    public void unableToFindBank() {
        assertNull(bankService.findBankById(BAD_ID));
    }

    @DisplayName("Nothing will happen when you delete a non-existent bank.")
    @Test
    public void deleteNonExistentBank() {
        Bank bank = new Bank(BAD_ID, "this business is not in the database.");
        //prove again the bank is not in the database
        assertNull(bankService.findBankById(bank.getBankId()));
        bankService.deleteBank(bank.getBankId());
    }

    @DisplayName("Insert a bank and show that it is in the database by doing an select.")
    @Test
    public void insertBank() {
        bankService.insertBank(BANK);
        Bank foundBank = bankService.findBankById(BANK.getBankId());
        assertEquals(BANK, foundBank);
        bankService.deleteBank(foundBank.getBankId());
        assertNull(bankService.findBankById(foundBank.getBankId()));
    }

    @DisplayName("Upload a file from a stream.")
    @Test
    public void upload() {
        assertNull(bankService.findBankById(BANK.getBankId()));
        assertNull(bankService.findBankById(BANK2.getBankId()));
        InputStream inputStream = new ByteArrayInputStream(UPLOAD.getBytes(Charset.forName("UTF-8")));

        bankService.upload(inputStream);

        assertEquals(BANK, bankService.findBankById(BANK.getBankId()));
        assertEquals(BANK2, bankService.findBankById(BANK2.getBankId()));

        bankService.deleteBank(BANK.getBankId());
        bankService.deleteBank(BANK2.getBankId());

        assertNull(bankService.findBankById(BANK.getBankId()));
        assertNull(bankService.findBankById(BANK2.getBankId()));
    }

    @DisplayName("Display all Banks")
    @Test
    public void all() {
        assertNull(bankService.findBankById(BANK.getBankId()));
        assertNull(bankService.findBankById(BANK2.getBankId()));
        InputStream inputStream = new ByteArrayInputStream(UPLOAD.getBytes(Charset.forName("UTF-8")));

        bankService.upload(inputStream);

        List<Bank> bankList = bankService.findAll();
        assertNotNull(bankList);
        assertEquals(2, bankList.size());
        assertTrue(bankList.contains(BANK));
        assertTrue(bankList.contains(BANK2));

        bankService.deleteBank(BANK.getBankId());
        bankService.deleteBank(BANK2.getBankId());
        bankList = bankService.findAll();
        assertNotNull(bankList);
        assertTrue(bankList.isEmpty());
    }
}
