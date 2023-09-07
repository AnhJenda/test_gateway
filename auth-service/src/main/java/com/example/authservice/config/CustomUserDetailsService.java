package com.example.authservice.config;

import com.example.authservice.entities.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SignupRequest credential = restTemplate.getForObject("http://localhost:8801/users/getByEmail", SignupRequest.class, username);
        return (UserDetails) Mono.just(credential).map(CustomUserDetails::new);
    }
}
