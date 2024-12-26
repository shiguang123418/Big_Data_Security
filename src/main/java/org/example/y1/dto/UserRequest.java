package org.example.y1.dto;

public class UserRequest {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String age;
    private String tel;
    private String idcard;
    private String address;
    private String account;

    public String getRole() {
        return role;
    }

    private String password;
    private String role;

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public String getTel() {
        return tel;
    }

    public String getIdcard() {
        return idcard;
    }
}
