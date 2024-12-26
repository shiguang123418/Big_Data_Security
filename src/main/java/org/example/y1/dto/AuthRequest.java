package org.example.y1.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String username;
    private String keyId;
    private String account;
    private String  age;
    private String role;
    private String tel;
    private String address;

    public String getIdcard() {
        return idcard;
    }

    private String gender;
    private String idcard;

    public String getUsername() {
        return username;
    }

    public String getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getTel() {
        return tel;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }

    public String getAccount() {
        return account;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeyId() {
        return keyId;
    }

}