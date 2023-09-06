package com.example.authservice.entities.value_object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    @author: Dinh Quang Anh
    Date   : 9/6/2023
    Project: auth-service
*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserVO {
    private String id;
    private String email;
    private String password;
    private String role;

}
