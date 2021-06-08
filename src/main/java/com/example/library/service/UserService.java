package com.example.library.service;

import com.example.library.entity.User;
import com.example.library.entity.request.UserDTO;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

 /*
    Project     : Library Service
    Class       : UserService.java
    Author      : Johan Eka Santosa
    Created On  : Mon, 07-06-2021

    Version History:
        v1.0.0 => Initial Work (Mon, 07-06-2021 by Johan Eka Santosa)
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final String ServiceName = "UserService";

    // Feature for creating new user
    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity registerUser(@RequestBody UserDTO userDTO) {
        String methodName = "registerUser";

        ResponseEntity res = ResponseEntity.ok().build();

        userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

        System.out.println("=== START SERVICE [" + ServiceName + "] ===");
        System.out.println("[" + ServiceName + "] START METHOD " + methodName);

        System.out.println("[" + ServiceName + " - " + methodName + "] Input: " + userDTO.toString());

        System.out.println("[" + ServiceName + " - " + methodName + "] Looking email [" + userDTO.getEmail() + "] at DB");
        User user = userRepository.findUserByEmail(userDTO.getEmail());

        if(user == null) {
            Timestamp ts = new Timestamp((new Date()).getTime());

            System.out.println("[" + ServiceName + " - " + methodName + "] Saving to DB");
            user = new User();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setCreatedAt(ts);
            user.setUpdatedAt(ts);

            userRepository.save(user);
            res = ResponseEntity.ok(user);
        } else {
            System.out.println("[" + ServiceName + " - " + methodName + "] User not unique!");
            HashMap<String, String> hsOutput = new HashMap<>();
            hsOutput.put("error_key", "ER-00-400");
            hsOutput.put("error_msg", "User email already exist!");

            res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hsOutput);
        }

        System.out.println("[" + ServiceName + " - " + methodName + "] Returned with message: " + res);
        System.out.println("=== FINISH METHOD [ " + methodName + " ] ===");
        System.out.println("=== FINISH SERVICE [" + ServiceName + "] ===");

        return res;
    }
}
