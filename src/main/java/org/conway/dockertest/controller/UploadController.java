package org.conway.dockertest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("domainType") String domainType) {

        if (!"customer".equalsIgnoreCase(domainType)) {
            return new ResponseEntity<>(String.format("%s is not an acceptable domainType", domainType), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("file uploaded", HttpStatus.OK);
    }
}
