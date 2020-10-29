package org.conway.dockertest.controller;

import org.conway.dockertest.domain.AccountBill;
import org.conway.dockertest.domain.Customer;
import org.conway.dockertest.domain.CustomerAccount;
import org.conway.dockertest.service.CustomerAccountBillService;
import org.conway.dockertest.service.CustomerAccountService;
import org.conway.dockertest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerAccountService accountService;
    @Autowired
    private CustomerAccountBillService accountBillService;

    @GetMapping("")
    public ResponseEntity<List<Customer>> all() {

        List<Customer> customerList = customerService.findAll();

        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            customerService.upload(file.getInputStream());
        } catch (IOException e) {
            return new ResponseEntity<>("Issue uploading data from file: " + e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("file uploaded", HttpStatus.OK);
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<Object> read(@PathVariable long id) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            return new ResponseEntity<>(String.format("There is no customer with id of %d", id), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>("Customer deleted.", HttpStatus.OK);
    }

    @PostMapping("/{id}/accounts")
    public ResponseEntity<Object> findAllAccountsForCustomerId(@PathVariable long id) {
        List<CustomerAccount> accounts = accountService.findCustomerAccountByCustomerId(id);
        if (accounts.isEmpty()) {
            return new ResponseEntity<>(String.format("No accounts found for customer with id %d", id), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        }
    }

    @PostMapping("/{id}/bills")
    public ResponseEntity<Object> findAllBillsForCustomerId(@PathVariable long id) {
        List<AccountBill> bills = accountBillService.findBillsByCustomerId(id);
        if (bills.isEmpty()) {
            return new ResponseEntity<>(String.format("No bills found for customer with id %d", id), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bills, HttpStatus.OK);
        }
    }

    @PostMapping("/{id}/billsDueInDays/{numDays}")
    public ResponseEntity<Object> findAllBillsForCustomerDueInDays(@PathVariable long id, @PathVariable int numDays) {
        List<AccountBill> bills = accountBillService.findCustomerUnpaidBillsDueInDays(id, numDays);
        if (bills.isEmpty()) {
            return new ResponseEntity<>(String.format("No bills found for customer with id %d that are due in %d days", id, numDays), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bills, HttpStatus.OK);
        }
    }
}
