package org.conway.dockertest.controller;

import org.conway.dockertest.domain.Bank;
import org.conway.dockertest.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping("")
    public ResponseEntity<List<Bank>> all() {

        List<Bank> bankList = bankService.findAll();

        return new ResponseEntity<>(bankList, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            bankService.upload(file.getInputStream());
        } catch (IOException e) {
            return new ResponseEntity<>("Issue uploading data from file: " + e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("file uploaded", HttpStatus.OK);
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<Object> read(@PathVariable long id) {
        Bank bank = bankService.findBankById(id);
        if (bank == null) {
            return new ResponseEntity<>(String.format("There is no bank with id of %d", id), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bank, HttpStatus.OK);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        bankService.deleteBank(id);
        return new ResponseEntity<>("Bank deleted.", HttpStatus.OK);
    }
}
