package org.conway.dockertest.service;

import org.conway.dockertest.domain.AccountBill;
import org.conway.dockertest.domain.CustomerAccount;
import org.conway.dockertest.domain.CustomerBill;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("development")
public class CustomerAccountBillServiceTest {
    private static final long CUSTOMER_ID1 = 1000;
    private static final long CUSTOMER_ID2 = 1001;
    private static final CustomerAccount ACCOUNT = new CustomerAccount(1, CUSTOMER_ID1, "Widget Supplier", "Widgets R US");
    private static final CustomerAccount ACCOUNT2 = new CustomerAccount(2, CUSTOMER_ID2, "Parts Supplier", "Widgets R US");
    private static final Long BAD_ID = 8675309L;

    private static final String UPLOAD_ACCOUNTS_CSV = String.format("Customer Account Id, Customer Id,Account Name,Business Name\n%d,%d,%s,%s\n%d,%d,%s,%s",
            ACCOUNT.getCustomerAccountId(), ACCOUNT.getCustomerId(), ACCOUNT.getAccountName(), ACCOUNT.getBusinessName(),
            ACCOUNT2.getCustomerAccountId(), ACCOUNT2.getCustomerId(), ACCOUNT2.getAccountName(), ACCOUNT2.getBusinessName());

    private static final String FORMAT = "MM/dd/yyyy";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FORMAT);
    private static final Date DATE_PLUS_60 = new Date(LocalDate.now().plusDays(60).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    private static final Date DATE_PLUS_20 = new Date(LocalDate.now().plusDays(20).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    private static final Date DATE_PLUS_10 = new Date(LocalDate.now().plusDays(10).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    private static final Date DATE_MINUS_20 = new Date(LocalDate.now().minusDays(20).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    private static final CustomerBill BILL = new CustomerBill(1, 1, false, 100000, DATE_PLUS_60);
    private static final CustomerBill BILL2 = new CustomerBill(2, 2, true, 50000, DATE_PLUS_20);
    private static final CustomerBill BILL3 = new CustomerBill(3, 2, false, 150000, DATE_PLUS_10);
    private static final CustomerBill BILL4 = new CustomerBill(4, 2, false, 250000, DATE_MINUS_20);

    private static final String UPLOAD_BILLS_CSV = String.format("Customer Billing Id, Customer Account Id,Paid In Full,Amount Due (Pennies),Due Date\n" +
                    "%d,%d,%s,%s,%s\n%d,%d,%s,%s,%s\n%d,%d,%s,%s,%s\n%d,%d,%s,%s,%s",
            BILL.getCustomerBillId(), BILL.getCustomerAccountId(), BILL.isPaid(), BILL.getAmountDuePennies(), DATE_FORMAT.format(BILL.getDueDate()),
            BILL2.getCustomerBillId(), BILL2.getCustomerAccountId(), BILL2.isPaid(), BILL2.getAmountDuePennies(), DATE_FORMAT.format(BILL2.getDueDate()),
            BILL3.getCustomerBillId(), BILL3.getCustomerAccountId(), BILL3.isPaid(), BILL3.getAmountDuePennies(), DATE_FORMAT.format(BILL3.getDueDate()),
            BILL4.getCustomerBillId(), BILL4.getCustomerAccountId(), BILL4.isPaid(), BILL4.getAmountDuePennies(), DATE_FORMAT.format(BILL4.getDueDate()));

    @Autowired
    private CustomerAccountService accountService;

    @Autowired
    private CustomerBillService billService;

    @Autowired
    private CustomerAccountBillService accountBillService;

    @AfterEach
    public void clearTestCustomerAccountsFromDatabase() {
        accountService.deleteCustomerAccount(ACCOUNT.getCustomerAccountId());
        accountService.deleteCustomerAccount(ACCOUNT2.getCustomerAccountId());
        billService.deleteCustomerBill(BILL.getCustomerBillId());
        billService.deleteCustomerBill(BILL2.getCustomerBillId());
        billService.deleteCustomerBill(BILL3.getCustomerBillId());
        billService.deleteCustomerBill(BILL4.getCustomerBillId());
    }

    @BeforeEach
    public void uploadData() {
        InputStream inputStream = new ByteArrayInputStream(UPLOAD_ACCOUNTS_CSV.getBytes(Charset.forName("UTF-8")));
        accountService.upload(inputStream);
        inputStream = new ByteArrayInputStream(UPLOAD_BILLS_CSV.getBytes(Charset.forName("UTF-8")));
        billService.upload(inputStream);
    }

    @DisplayName("Try and find a non-existent customer will return an empty list.")
    @Test
    public void unableToFindCustomerAccount() {
        List<AccountBill> bills = accountBillService.findBillsByCustomerId(BAD_ID);
        assertTrue(bills.isEmpty());
    }

    @DisplayName("Display all bills for a customer")
    @Test
    public void all() {
        assertEquals(1, accountBillService.findBillsByCustomerId(CUSTOMER_ID1).size());
        assertEquals(3, accountBillService.findBillsByCustomerId(CUSTOMER_ID2).size());
    }
}
