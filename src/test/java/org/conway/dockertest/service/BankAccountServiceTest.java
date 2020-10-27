package org.conway.dockertest.service;

import org.conway.dockertest.domain.BankAccount;
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
public class BankAccountServiceTest {
    private static final BankAccount BANK_ACCOUNT = new BankAccount(1, 1000, 1, "Simple Checking Account", "123456789", "000123458675309");
    private static final BankAccount BANK_ACCOUNT2 = new BankAccount(2, 1001, 1, "Main Checking Account", "123456789", "000967658675309");
    private static final Long BAD_ID = 8675309L;

    private static final String UPLOAD = String.format("Bank Account Id,Customer Id,Bank Id,Account Name,Routing Number,Account Number\n%d,%d,%d,%s,%s,%s\n%d,%d,%d,%s,%s,%s",
            BANK_ACCOUNT.getBankAccountId(), BANK_ACCOUNT.getCustomerId(), BANK_ACCOUNT.getBankId(),
            BANK_ACCOUNT.getName(), BANK_ACCOUNT.getRoutingNumber(), BANK_ACCOUNT.getAccountNumber(),
            BANK_ACCOUNT2.getBankAccountId(), BANK_ACCOUNT2.getCustomerId(), BANK_ACCOUNT2.getBankId(),
            BANK_ACCOUNT2.getName(), BANK_ACCOUNT2.getRoutingNumber(), BANK_ACCOUNT2.getAccountNumber());

    @Autowired
    private BankAccountService bankAccountService;

    @AfterEach
    public void clearTestBankAccountsFromDatabase() {
        bankAccountService.deleteBankAccount(BANK_ACCOUNT.getBankAccountId());
        bankAccountService.deleteBankAccount(BANK_ACCOUNT2.getBankAccountId());
    }

    @DisplayName("Try and find a non-existent bankAccount will return null.")
    @Test
    public void unableToFindBankAccount() {
        assertNull(bankAccountService.findBankAccountById(BAD_ID));
    }

    @DisplayName("Nothing will happen when you delete a non-existent bankAccount.")
    @Test
    public void deleteNonExistentBankAccount() {
        //prove again the bankAccount is not in the database
        assertNull(bankAccountService.findBankAccountById(BAD_ID));
        bankAccountService.deleteBankAccount(BAD_ID);
    }

    @DisplayName("Insert a bankAccount and show that it is in the database by doing an select.")
    @Test
    public void insertBankAccount() {
        bankAccountService.insertBankAccount(BANK_ACCOUNT);
        BankAccount foundBankAccount = bankAccountService.findBankAccountById(BANK_ACCOUNT.getBankAccountId());
        assertEquals(BANK_ACCOUNT, foundBankAccount);
        bankAccountService.deleteBankAccount(foundBankAccount.getBankAccountId());
        assertNull(bankAccountService.findBankAccountById(foundBankAccount.getBankAccountId()));
    }

    @DisplayName("Upload a file from a stream.")
    @Test
    public void upload() {
        assertNull(bankAccountService.findBankAccountById(BANK_ACCOUNT.getBankAccountId()));
        assertNull(bankAccountService.findBankAccountById(BANK_ACCOUNT2.getBankAccountId()));
        InputStream inputStream = new ByteArrayInputStream(UPLOAD.getBytes(Charset.forName("UTF-8")));

        bankAccountService.upload(inputStream);

        assertEquals(BANK_ACCOUNT, bankAccountService.findBankAccountById(BANK_ACCOUNT.getBankAccountId()));
        assertEquals(BANK_ACCOUNT2, bankAccountService.findBankAccountById(BANK_ACCOUNT2.getBankAccountId()));

        bankAccountService.deleteBankAccount(BANK_ACCOUNT.getBankAccountId());
        bankAccountService.deleteBankAccount(BANK_ACCOUNT2.getBankAccountId());

        assertNull(bankAccountService.findBankAccountById(BANK_ACCOUNT.getBankAccountId()));
        assertNull(bankAccountService.findBankAccountById(BANK_ACCOUNT2.getBankAccountId()));
    }

    @DisplayName("Display all BankAccounts")
    @Test
    public void all() {
        assertNull(bankAccountService.findBankAccountById(BANK_ACCOUNT.getBankAccountId()));
        assertNull(bankAccountService.findBankAccountById(BANK_ACCOUNT2.getBankAccountId()));
        InputStream inputStream = new ByteArrayInputStream(UPLOAD.getBytes(Charset.forName("UTF-8")));

        bankAccountService.upload(inputStream);

        List<BankAccount> bankAccountList = bankAccountService.findAll();
        assertNotNull(bankAccountList);
        assertEquals(2, bankAccountList.size());
        assertTrue(bankAccountList.contains(BANK_ACCOUNT));
        assertTrue(bankAccountList.contains(BANK_ACCOUNT2));

        bankAccountService.deleteBankAccount(BANK_ACCOUNT.getBankAccountId());
        bankAccountService.deleteBankAccount(BANK_ACCOUNT2.getBankAccountId());
        bankAccountList = bankAccountService.findAll();
        assertNotNull(bankAccountList);
        assertTrue(bankAccountList.isEmpty());
    }
}
