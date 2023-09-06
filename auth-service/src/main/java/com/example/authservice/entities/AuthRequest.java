package com.example.authservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
    @author: Dinh Quang Anh
    Date   : 9/6/2023
    Project: auth-service
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest implements Serializable {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}
