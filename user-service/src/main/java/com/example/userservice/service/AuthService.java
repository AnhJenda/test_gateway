package com.example.userservice.service;

import com.example.userservice.config.CustomUserDetails;
import com.example.userservice.config.CustomUserDetailsService;
import com.example.userservice.entities.JwtResponse;
import com.example.userservice.entities.LoginRequest;
import com.example.userservice.entities.SignupRequest;
import com.example.userservice.entities.User;
import com.example.userservice.repository.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

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
    private UserRepository repository;
    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    public AuthService(RestTemplate restTemplate, final JwtUtils jwt) {
        this.restTemplate = restTemplate;
        this.jwt = jwt;
    }

    public JwtResponse login(LoginRequest request) {
        try {
            User userOptional = repository.findUserByEmail(request.getUsername());

            if (Objects.nonNull(userOptional)) {
                String requestPasswordEncoded = passwordEncoder.encode(request.getPassword());

                if (requestPasswordEncoded.equals(userOptional.getPassword())) {
                     return new JwtResponse(
                            jwt.generateToken(userOptional.getEmail()),
                             userOptional.getId(),
                             userOptional.getEmail(),
                             userOptional.getFirstName(),
                             userOptional.getLastName(),
                             userOptional.getRole()
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Trả về null nếu không tìm thấy người dùng hoặc mật khẩu không khớp
        return null;
    }


    public String saveUser(SignupRequest signupRequest) {

        User userOptional = repository.findUserByEmail(signupRequest.getEmail());
        if (Objects.isNull(userOptional)){
            User user = new User();
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setEmail(signupRequest.getEmail());
            user.setFirstName(signupRequest.getFirstName());
            user.setLastName(signupRequest.getLastName());
            user.setRole(signupRequest.getRole());
            repository.save(user);
            return "user added to the system";
        }
        return "user existed";
    }

    public String generateToken(String username) {
        return jwt.generateToken(username);
    }

    public void validateToken(String token) {
        jwt.validateToken(token);
    }
}
