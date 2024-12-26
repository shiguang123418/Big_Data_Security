package org.example.y1.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.y1.model.User;
import org.example.y1.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.example.y1.util.RSAKeyManager;
import org.example.y1.key.key;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;


@Service
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RSAKeyManager rsaKeyManager;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SessionService sessionService;
    private key key=new key();

    public User findByAccount(String account)  {
        String sql = "SELECT id,account, password FROM users WHERE account = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            User user = new User();
            user.setAccount(rs.getString("account"));
            try {
                user.setPassword(rsaKeyManager.decrypt1(key.getPrivate_key(), rs.getString("password")));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            user.setId(rs.getLong("id"));
            return user;
        }, account);
    }

    public String updateUser(User user) {
        System.out.println(user);
        String sql = "UPDATE user_dict SET name=?,email=?,age=?,tel=?,idcard=?,gender=?,address=? WHERE id = ?";


        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getAge(), user.getTel(), user.getIdcard(), user.getGender(), user.getAddress(), user.getId());

        String sq1;
        if (existsById(user.getId())) {
            sq1 = "UPDATE users SET account=?,password=? WHERE id=?";
            try {
                String password = rsaKeyManager.encrypt(key.getPublic_key(), user.getPassword());
                jdbcTemplate.update(sq1, user.getAccount(), password, user.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            sq1 = "INSERT INTO users (id, account, password) VALUES (?, ?, ?)";
            try {
                String password = rsaKeyManager.encrypt(key.getPublic_key(), user.getPassword());
                jdbcTemplate.update(sq1, user.getId(), user.getAccount(), password);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return "success";
    }
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }

    public boolean existsByaccount(String account) {
        String sql = "SELECT COUNT(*) FROM users WHERE account = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, account);
        return count > 0;
    }

    public boolean save(User user) {
        try {
            String password = rsaKeyManager.encrypt(key.getPublic_key(), user.getPassword());
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String sql = "INSERT INTO user_dict (name, email, gender, age, tel, idcard, address) VALUES (?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getGender());
                ps.setString(4, user.getAge());
                ps.setString(5, user.getTel());
                ps.setString(6, user.getIdcard());
                ps.setString(7, user.getAddress());
                return ps;
            }, keyHolder);

            long userId = keyHolder.getKey().longValue();
            if (user.getAddress()==null){
                return true;
            }
            String sql2 = "INSERT INTO users (id, account, password) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql2, userId, user.getAccount(), password);

            String sql3 = "INSERT INTO permissions (id, permission) VALUES (?, ?)";
            jdbcTemplate.update(sql3, userId,"3");

            return true;
        } catch (Exception e) {
            return false;
        }
}

    public void delete(Long id) {
        String sql = "DELETE FROM user_dict WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    public Long findIdBySessionId(HttpServletRequest request) {
        String id = sessionService.getSessionIdFromCookies(request);
        String userJson = (String) redisTemplate.opsForValue().get("session:" + id);
        User user = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            user = objectMapper.readValue(userJson, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Long userid=user.getId();
        return userid;
    }

    public int getrole(Long id) {
        String sql = "SELECT permission FROM permissions WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id);
    }

    public void updateRole(Long id, String role) {
        String sql = "UPDATE permissions SET permission = ? WHERE id = ?";
        jdbcTemplate.update(sql, role, id);

    }

}