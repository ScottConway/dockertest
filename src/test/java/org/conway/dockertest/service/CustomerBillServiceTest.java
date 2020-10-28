package org.conway.dockertest.service;

import org.conway.dockertest.domain.CustomerBill;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("development")
public class CustomerBillServiceTest {
    private static final String FORMAT = "MM/dd/yyyy";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FORMAT);
    private static final Date DATE1 = new Date(new GregorianCalendar(2020, 11, 15).getTimeInMillis());
    private static final Date DATE2 = new Date(new GregorianCalendar(2020, 9, 15).getTimeInMillis());
    private static final CustomerBill BILL = new CustomerBill(1, 1, false, 100000, DATE1);
    private static final CustomerBill BILL2 = new CustomerBill(2, 2, true, 50000, DATE2);
    private static final Long BAD_ID = 8675309L;

    private static final String UPLOAD = String.format("Customer Billing Id, Customer Account Id,Paid In Full,Amount Due (Pennies),Due Date\n%d,%d,%s,%s,%s\n%d,%d,%s,%s,%s",
            BILL.getCustomerBillId(), BILL.getCustomerAccountId(), BILL.isPaid(), BILL.getAmountDuePennies(), DATE_FORMAT.format(BILL.getDueDate()),
            BILL2.getCustomerBillId(), BILL2.getCustomerAccountId(), BILL2.isPaid(), BILL2.getAmountDuePennies(), DATE_FORMAT.format(BILL2.getDueDate()));

    @Autowired
    private CustomerBillService billService;

    @AfterEach
    public void clearTestCustomerBillsFromDatabase() {
        billService.deleteCustomerBill(BILL.getCustomerBillId());
        billService.deleteCustomerBill(BILL2.getCustomerBillId());
    }

    @DisplayName("Try and find a non-existent customer will return null.")
    @Test
    public void unableToFindCustomerBill() {
        assertNull(billService.findCustomerBillById(BAD_ID));
    }

    @DisplayName("Nothing will happen when you delete a non-existent customer.")
    @Test
    public void deleteNonExistentCustomerBill() {
        CustomerBill customer = new CustomerBill(BAD_ID, 9999, false, 100000, DATE1);
        //prove again the customer is not in the database
        assertNull(billService.findCustomerBillById(customer.getCustomerBillId()));
        billService.deleteCustomerBill(customer.getCustomerBillId());
    }

    @DisplayName("Insert a customer and show that it is in the database by doing an select.")
    @Test
    public void insertCustomerBill() {
        billService.insertCustomerBill(BILL);
        CustomerBill foundCustomerBill = billService.findCustomerBillById(BILL.getCustomerBillId());
        assertEquals(BILL, foundCustomerBill);
        billService.deleteCustomerBill(foundCustomerBill.getCustomerBillId());
        assertNull(billService.findCustomerBillById(foundCustomerBill.getCustomerBillId()));
    }

    @DisplayName("Upload a file from a stream.")
    @Test
    public void upload() {
        assertNull(billService.findCustomerBillById(BILL.getCustomerBillId()));
        assertNull(billService.findCustomerBillById(BILL2.getCustomerBillId()));
        InputStream inputStream = new ByteArrayInputStream(UPLOAD.getBytes(Charset.forName("UTF-8")));

        billService.upload(inputStream);

        assertEquals(BILL, billService.findCustomerBillById(BILL.getCustomerBillId()));
        assertEquals(BILL2, billService.findCustomerBillById(BILL2.getCustomerBillId()));

        billService.deleteCustomerBill(BILL.getCustomerBillId());
        billService.deleteCustomerBill(BILL2.getCustomerBillId());

        assertNull(billService.findCustomerBillById(BILL.getCustomerBillId()));
        assertNull(billService.findCustomerBillById(BILL2.getCustomerBillId()));
    }

    @DisplayName("Display all CustomerBills")
    @Test
    public void all() {
        assertNull(billService.findCustomerBillById(BILL.getCustomerBillId()));
        assertNull(billService.findCustomerBillById(BILL2.getCustomerBillId()));
        InputStream inputStream = new ByteArrayInputStream(UPLOAD.getBytes(Charset.forName("UTF-8")));

        billService.upload(inputStream);

        List<CustomerBill> customerList = billService.findAll();
        assertNotNull(customerList);
        assertEquals(2, customerList.size());
        assertTrue(customerList.contains(BILL));
        assertTrue(customerList.contains(BILL2));

        billService.deleteCustomerBill(BILL.getCustomerBillId());
        billService.deleteCustomerBill(BILL2.getCustomerBillId());
        customerList = billService.findAll();
        assertNotNull(customerList);
        assertTrue(customerList.isEmpty());
    }
}
