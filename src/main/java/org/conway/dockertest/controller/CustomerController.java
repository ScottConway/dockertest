package org.conway.dockertest.controller;

import org.conway.dockertest.domain.Customer;
import org.conway.dockertest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            customerService.upload(file.getInputStream());
        } catch (IOException e) {
            return new ResponseEntity<>("Issue uploading data from file: " + e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("file uploaded", HttpStatus.OK);
    }

    @PostMapping("/read")
    public ResponseEntity<Object> read(@RequestParam("customerId") long id) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            return new ResponseEntity<>(String.format("There is no customer with id of %d", id), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("customerId") long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>("Customer deleted.", HttpStatus.OK);
    }
}
