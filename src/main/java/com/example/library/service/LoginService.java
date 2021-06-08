package com.example.library.service;

import com.example.library.entity.Login;
import com.example.library.entity.User;
import com.example.library.entity.request.LoginDTO;
import com.example.library.repository.LoginRepository;
import com.example.library.repository.UserRepository;
import com.example.library.util.JWTTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@RestController
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRepository loginRepository;

    private final String ServiceName = "LoginService";

    @PostMapping(value = "/login", produces = "application/json")
    @Transactional
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        ResponseEntity res = null;
        String methodName = "login";

        System.out.println("=== START SERVICE [" + ServiceName + "] ===");
        System.out.println("[" + ServiceName + "] START METHOD " + methodName);

        System.out.println("[" + ServiceName + " - " + methodName + "] Input: " + loginDTO.toString());

        String decoded = new String(Base64.getDecoder().decode(loginDTO.getLoginCredential()));
        String[] value = decoded.split(":");
        String userEmail = value[0];
        String password = value[1];


        System.out.println("[" + ServiceName + " - " + methodName + "] Looking user at DB by email: " + userEmail);
        User u = userRepository.findUserByEmail(userEmail);

        if(u == null) {
            System.out.println("[" + ServiceName + " - " + methodName + "] Email not found");

            HashMap<String, String> hsOutput = new HashMap<>();
            hsOutput.put("error_key", "ER-00-403");
            hsOutput.put("error_msg", "Email not found");
            res = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(hsOutput);
        } else {
            if((new BCryptPasswordEncoder()).matches(password, u.getPassword())) {
                System.out.println("[" + ServiceName + " - " + methodName + "] Login permitted");
                System.out.println("[" + ServiceName + " - " + methodName + "] Saving login info to DB");
                Login _login = loginRepository.save(new Login(u.getId()));

                System.out.println("[" + ServiceName + " - " + methodName + "] Generating JWT");
                String currentTime = Long.toString((new Date()).getTime());

                HashMap<String, String> claims = new HashMap<>();
                claims.put("sessionId", _login.getSessionId());
                claims.put("createdAt", _login.getCreatedAt().toString());
                claims.put("isvalid", currentTime.substring(0,6) + _login.getIsValid() + currentTime.substring(6));
                String jwt = JWTTokenizer.generateJWT(claims);

                HashMap<String, String> hsOutout = new HashMap<>();
                hsOutout.put("token", jwt);

                res = ResponseEntity.ok(hsOutout);
            } else {
                System.out.println("[" + ServiceName + " - " + methodName + "] Wrong password");

                HashMap<String, String> hsOutput = new HashMap<>();
                hsOutput.put("error_key", "ER-00-403");
                hsOutput.put("error_msg", "Wrong password");
                res = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(hsOutput);
            }
        }

        System.out.println("[" + ServiceName + " - " + methodName + "] Returned with message: " + res);
        System.out.println("=== FINISH METHOD [ " + methodName + " ] ===");
        System.out.println("=== FINISH SERVICE [" + ServiceName + "] ===");
        return res;
    }
}
