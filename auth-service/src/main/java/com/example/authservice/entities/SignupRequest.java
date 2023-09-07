package com.example.authservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/*
    @author: Dinh Quang Anh
    Date   : 8/4/2023
    Project: spring_school_api
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<String> role;
}
