package org.example.y1.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.y1.dto.AuthRequest;
import org.example.y1.dto.AuthResponse;
import org.example.y1.key.key;
import org.example.y1.model.User;
import lombok.RequiredArgsConstructor;
import org.example.y1.repository.UserDictRepository;
import org.example.y1.repository.UserRepository;
import org.example.y1.util.RSAKeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserDictRepository userDictRepository;
    private final SessionService sessionService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final key key=new key();
    @Autowired
    private RSAKeyManager rsaKeyManager;

    public ResponseEntity<?> login(HttpServletRequest request,String encryptedaccount, String encryptedPassword, String keyId) {
        Map<String, Object> response = new HashMap<>();
        try {
            String account = rsaKeyManager.decrypt(keyId,encryptedaccount);
            String password = rsaKeyManager.decrypt(keyId,encryptedPassword);

            User user = userRepository.findByAccount(account);

            redisTemplate.delete("RSA_PRIVATE_KEY:"+keyId);
            if (user != null && user.getPassword().equals(password)) {
                User user1= userDictRepository.findByID(user.getId());

                sessionService.createSession(user1,request);
                response.put("success", true);
                response.put("message", "登录成功");
                response.put("_is", true);

                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "账号或密码错误");
                response.put("_is", false);
                return ResponseEntity.badRequest().body(AuthResponse.error("账号或密码错误"));
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "登录失败，请重试");
            response.put("_is", false);
            return ResponseEntity.badRequest().body(AuthResponse.error("登录失败，请重试"));
        }
    }

    public ResponseEntity<?> register(AuthRequest request) {
        String keyId = request.getKeyId();
        Map<String, String> decryptedData = null;
        try {
            decryptedData = decryptRequestData(request, keyId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        redisTemplate.delete("RSA_PRIVATE_KEY:" + keyId);

        if (userRepository.existsByaccount(decryptedData.get("account"))) {
            return ResponseEntity.badRequest().body(AuthResponse.error("用户已存在"));
        }

        User user = createUser(decryptedData);
        if(userRepository.save(user)) {
            return ResponseEntity.ok(AuthResponse.success("注册成功"));
        }else{
            return ResponseEntity.badRequest().body(AuthResponse.error("注册失败，请重试"));
        }

    }

    private Map<String, String> decryptRequestData(AuthRequest request, String keyId) throws Exception {
        Map<String, String> decryptedData = new HashMap<>();
        decryptedData.put("account", rsaKeyManager.decrypt(keyId, request.getAccount()));
        decryptedData.put("email", rsaKeyManager.decrypt(keyId, request.getEmail()));
        decryptedData.put("password", rsaKeyManager.decrypt(keyId, request.getPassword()));
        decryptedData.put("username", rsaKeyManager.decrypt(keyId, request.getUsername()));
        decryptedData.put("gender", rsaKeyManager.decrypt(keyId, request.getGender()));
        decryptedData.put("age", rsaKeyManager.decrypt(keyId, String.valueOf(request.getAge())));
        decryptedData.put("tel", rsaKeyManager.decrypt(keyId, request.getTel()));
        decryptedData.put("address", request.getAddress());
        decryptedData.put("idcard", rsaKeyManager.decrypt(keyId, request.getIdcard()));
        return decryptedData;
    }

    private User createUser(Map<String, String> data) {
        User user = new User();
        user.setAccount(data.get("account"));
        user.setEmail(data.get("email"));
        user.setUsername(data.get("username"));
        user.setPassword(data.get("password"));
        user.setAge(data.get("age"));
        user.setGender(data.get("gender"));
        user.setTel(data.get("tel"));
        user.setAddress(data.get("address"));
        user.setIdcard(data.get("idcard"));
        return user;
    }

    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            sessionService.invalidateCurrentSession(request);
            return ResponseEntity.ok(AuthResponse.success("登出成功"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(AuthResponse.error("登出失败"));
        }
    }

    public void backup(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Get user ID and role
            Long userId = userRepository.findIdBySessionId(request);
            int role = userRepository.getrole(userId);

            // Set response headers with proper encoding
            response.setContentType("text/csv; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"users_backup.csv\"");

            // Get writer with proper encoding
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));

            // Write CSV header
            writer.println("id,name,age,tel,email,gender,idCard,address,role,account,password");

            // Get and write user data
            List<User> users = userDictRepository.findAll(role);
            for (User user : users) {
                // Escape special characters and wrap values in quotes to handle commas in data
                writer.println(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                        escapeCSV(user.getId().toString()),
                        escapeCSV(user.getUsername()),
                        escapeCSV(user.getAge().toString()),
                        escapeCSV(user.getTel()),
                        escapeCSV(user.getEmail()),
                        escapeCSV(user.getGender()),
                        escapeCSV(user.getIdcard()),
                        escapeCSV(user.getAddress()),
                        escapeCSV(user.getRole()),
                        escapeCSV(user.getAccount()),
                        escapeCSV(user.getPassword())
                ));
            }

            writer.flush();
            writer.close();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException("备份失败: " + e.getMessage(), e);
        }
    }

    private String escapeCSV(String value) {
        if (value == null) return "";
        return value.replace("\"", "\"\""); // Escape quotes in CSV
    }

    public ResponseEntity<?> recover(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("文件不能为空");
        }

        int successCount = 0;
        int errorCount = 0;

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String[] data;

            // Skip header
            reader.readNext();

            while ((data = reader.readNext()) != null) {
                try {
                    // 移除可能存在的双引号
                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i].replaceAll("\"", "").trim();
                    }

                    // 数据解析
                    User user = new User();
                    user.setId(Long.parseLong(data[0]));
                    user.setUsername(data[1]);
                    user.setAge(data[2]);
                    user.setTel(data[3]);
                    user.setEmail(data[4]);
                    user.setGender(data[5]);
                    user.setIdcard(data[6]);
                    user.setAddress(data[7]);
                    user.setRole(data[8]);
                    if (data.length > 9) {
                        user.setAccount(data[9]);
                        user.setPassword(data[10]);
                    }
                    // 保存用户数据到数据库
                    userDictRepository.save(user);
                    successCount++;
                } catch (Exception e) {
                    // 数据解析或保存失败
                    errorCount++;
                }
            }

            return ResponseEntity.ok("成功恢复: " + successCount + " 条数据, 恢复失败: " + errorCount + " 条数据");
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件解析失败");
        }
    }

}