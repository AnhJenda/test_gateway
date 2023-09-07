package com.example.authservice.service;

import com.example.authservice.entities.JwtResponse;
import com.example.authservice.entities.LoginRequest;
import com.example.authservice.entities.SignupRequest;
import com.example.authservice.entities.value_object.UserVO;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*    @author: Dinh Quang Anh
    Date   : 9/6/2023
    Project: auth-service
*/

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtils jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(RestTemplate restTemplate, final JwtUtils jwt) {
        this.restTemplate = restTemplate;
        this.jwt = jwt;
    }

    public JwtResponse login (LoginRequest request){
        UserVO userVO = restTemplate.getForObject("http://localhost:8803/users/getByEmail", UserVO.class, request.getUsername());
        Assert.isNull(userVO, "User not found!");
        return new JwtResponse(jwt.generateToken(userVO.getEmail()),
                userVO.getId(),
                userVO.getEmail(),
                userVO.getFirstName(),
                userVO.getLastName(),
                userVO.getRole());
    }

    public String saveUser(SignupRequest signupRequest) {
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        UserVO userVO = restTemplate.postForObject("http://localhost:8801/users/save", signupRequest, UserVO.class);
        Assert.notNull(userVO, "Failed to register user. Please try again later");
        return "user added to the system";
    }

    public String generateToken(String username) {
        return jwt.generateToken(username);
    }

    public void validateToken(String token) {
        jwt.validateToken(token);
    }
}
