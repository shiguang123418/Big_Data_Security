package org.example.y1.service;

import lombok.RequiredArgsConstructor;
import org.example.y1.dto.UserRequest;
import org.example.y1.model.User;
import org.example.y1.repository.UserDictRepository;
import org.example.y1.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDictRepository userDictRepository;
    private final UserRepository userRepository;
    private final SessionService sessionService;

    public ResponseEntity<?> getUsers(HttpServletRequest request) {
        Map<String, Object> response = sessionService.get_Session(request);
        if (response.get("_is").equals(false)) {
            return ResponseEntity.ok(response);
        }

        Long userid=userRepository.findIdBySessionId(request);

        int role = userRepository.getrole(userid);

        List<User> arr=userDictRepository.findAll(role);

        response = new HashMap<>();
        if(arr.size()==0){
            response.put("success", false);
            response.put("message","未找到用户");
            response.put("_is",false);
            return ResponseEntity.ok(response);
        }

        response.put("success", true);
        response.put("_is",true);
        response.put("users",arr);
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        Map<String, Object> response = sessionService.get_Session(request);
        if (response.get("_is").equals(false)) {
            return ResponseEntity.ok(response);
        }

        String sessionid= sessionService.getSessionIdFromCookies(request);

        User user =sessionService.getCurrentUser(sessionid);
        response = new HashMap<>();
        if(user==null){
            response.put("success", false);
            response.put("message","未找到用户");
            response.put("_is",false);
            return ResponseEntity.ok(response);
        }
        response.put("success", true);
        response.put("_is",true);
        response.put("user",user);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> updateUser(HttpServletRequest request1,UserRequest request) {
        Map<String, Object> response = sessionService.get_Session(request1);
        if (response.get("_is").equals(false)) {
            return ResponseEntity.ok(response);
        }


        User user = new User();
        user.setId(request.getId());
        user.setEmail(request.getEmail());
        user.setUsername(request.getName());
        user.setAge(request.getAge());
        user.setTel(request.getTel());
        user.setIdcard(request.getIdcard());
        user.setAddress(request.getAddress());
        user.setGender(request.getGender());
        user.setAccount(request.getAccount());
        user.setPassword(request.getPassword());

        userRepository.updateUser(user);
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<?> deleteUser(HttpServletRequest request1,UserRequest request) {
        Map<String, Object> response = sessionService.get_Session(request1);
        if (response.get("_is").equals(false)) {
            return ResponseEntity.ok(response);
        }

        Long id= request.getId();
        userRepository.delete(id);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> updateRole(HttpServletRequest request1,UserRequest request) {
        Map<String, Object> response = sessionService.get_Session(request1);
        if (response.get("_is").equals(false)) {
            return ResponseEntity.ok(response);
        }
        try {
            Long id= request.getId();
            String  role= request.getRole();
            userRepository.updateRole(id,role);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message","更新失败");
            response.put("_is",false);
            return ResponseEntity.ok(response);
        }
        response= new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
}
