package com.example.authservice.entities.value_object;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/*
    @author: Dinh Quang Anh
    Date   : 9/6/2023
    Project: auth-service
*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserVO implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String videoId = null;
}
