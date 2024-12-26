package org.example.y1.controller;

import org.example.y1.dto.AuthRequest;
import org.example.y1.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.example.y1.service.SessionService;
import org.example.y1.service.UserService;
import org.example.y1.util.RSAKeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    private RSAKeyManager rsaKeyManager;


    @GetMapping("/get_public_key")
    public String  getPublicKey() {
        return rsaKeyManager.generateTemporaryKeyPair();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request1, @RequestBody AuthRequest request) {
        return authService.login(request1,request.getAccount(), request.getPassword(), request.getKeyId());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        return authService.register(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return authService.logout(request);
    }


    @GetMapping("/get_session")
    public ResponseEntity<Map<String, Object>> getSession(HttpServletRequest request) {
        return  sessionService.getSession(request);
    }

    @GetMapping("/backup")
    public void backup(HttpServletRequest request, HttpServletResponse response) {
        authService.backup(request, response);
    }

    @PostMapping("/recover")
    public ResponseEntity<?> restore(@RequestParam("file") MultipartFile file) {
        return authService.recover(file);
    }



}