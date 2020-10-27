package org.conway.dockertest.controller;

import org.conway.dockertest.domain.BankAccount;
import org.conway.dockertest.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("")
    public ResponseEntity<List<BankAccount>> all() {

        List<BankAccount> bankAccountList = bankAccountService.findAll();

        return new ResponseEntity<>(bankAccountList, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            bankAccountService.upload(file.getInputStream());
        } catch (IOException e) {
            return new ResponseEntity<>("Issue uploading data from file: " + e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("file uploaded", HttpStatus.OK);
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<Object> read(@PathVariable long id) {
        BankAccount bankAccount = bankAccountService.findBankAccountById(id);
        if (bankAccount == null) {
            return new ResponseEntity<>(String.format("There is no bankAccount with id of %d", id), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        bankAccountService.deleteBankAccount(id);
        return new ResponseEntity<>("BankAccount deleted.", HttpStatus.OK);
    }
}
