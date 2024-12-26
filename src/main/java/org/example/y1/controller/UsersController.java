package org.example.y1.controller;

import lombok.RequiredArgsConstructor;
import org.example.y1.dto.UserRequest;
import org.example.y1.service.AuthService;
import org.example.y1.service.SessionService;
import org.example.y1.service.UserService;
import org.example.y1.util.RSAKeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsersController {
    private final AuthService authService;
    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    private RSAKeyManager rsaKeyManager;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(HttpServletRequest request) {
        return userService.getUsers(request);
    }


    @GetMapping("/current_user")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        return userService.getCurrentUser(request);
    }

    @PostMapping("/update_user")
    public ResponseEntity<?> updateUser(HttpServletRequest request1,@RequestBody UserRequest request) {
        return userService.updateUser(request1,request);
    }


    @PostMapping("/delete_user")
    public ResponseEntity<?> deleteUser(HttpServletRequest request1,@RequestBody UserRequest request) {
        return userService.deleteUser(request1,request);
    }

    @PostMapping("/update_role")
    public ResponseEntity<?> updateRole(HttpServletRequest request1,@RequestBody UserRequest request) {
        return userService.updateRole(request1,request);
    }
}
