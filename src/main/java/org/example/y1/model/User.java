package org.example.y1.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String account;
    private String username;
    private String email;
    private String password;
    private String gender;
    private String age;
    private String tel;
    private String idcard;
    private String role;
    private String address;
    public User() {
    }

}