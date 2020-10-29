package org.conway.dockertest.controller;

import org.conway.dockertest.domain.CustomerAccount;
import org.conway.dockertest.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customer/account")
public class CustomerAccountController {

    @Autowired
    private CustomerAccountService customerAccountService;

    @GetMapping("")
    public ResponseEntity<List<CustomerAccount>> all() {

        List<CustomerAccount> customerAccountList = customerAccountService.findAll();

        return new ResponseEntity<>(customerAccountList, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            customerAccountService.upload(file.getInputStream());
        } catch (IOException e) {
            return new ResponseEntity<>("Issue uploading data from file: " + e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("file uploaded", HttpStatus.OK);
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<Object> read(@PathVariable long id) {
        CustomerAccount customerAccount = customerAccountService.findCustomerAccountById(id);
        if (customerAccount == null) {
            return new ResponseEntity<>(String.format("There is no customerAccount with id of %d", id), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerAccount, HttpStatus.OK);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        customerAccountService.deleteCustomerAccount(id);
        return new ResponseEntity<>("CustomerAccount deleted.", HttpStatus.OK);
    }
}
