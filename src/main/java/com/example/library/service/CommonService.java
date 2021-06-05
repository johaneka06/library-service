package com.example.library.service;

import com.example.library.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CommonService {

    @GetMapping(value = "/test", produces = "application/json")
    public ResponseEntity testFunction() {
        Test test = new Test("Halo", "Coba test");

        return ResponseEntity.status(HttpStatus.OK).body(test);
    }
}
