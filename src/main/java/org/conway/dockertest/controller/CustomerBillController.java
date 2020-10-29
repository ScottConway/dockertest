package org.conway.dockertest.controller;

import org.conway.dockertest.domain.CustomerBill;
import org.conway.dockertest.service.CustomerBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customer/bill")
public class CustomerBillController {

    @Autowired
    private CustomerBillService customerBillService;

    @GetMapping("")
    public ResponseEntity<List<CustomerBill>> all() {

        List<CustomerBill> customerBillList = customerBillService.findAll();

        return new ResponseEntity<>(customerBillList, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            customerBillService.upload(file.getInputStream());
        } catch (IOException e) {
            return new ResponseEntity<>("Issue uploading data from file: " + e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("file uploaded", HttpStatus.OK);
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<Object> read(@PathVariable long id) {
        CustomerBill customerBill = customerBillService.findCustomerBillById(id);
        if (customerBill == null) {
            return new ResponseEntity<>(String.format("There is no customerBill with id of %d", id), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerBill, HttpStatus.OK);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        customerBillService.deleteCustomerBill(id);
        return new ResponseEntity<>("CustomerBill deleted.", HttpStatus.OK);
    }
}
