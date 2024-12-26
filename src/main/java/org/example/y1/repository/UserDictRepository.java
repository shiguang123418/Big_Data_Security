package org.example.y1.repository;

import org.example.y1.model.User;
import org.example.y1.key.key;
import org.example.y1.util.DataMaskUtil;
import org.example.y1.util.RSAKeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserDictRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RSAKeyManager rsaKeyManager;
    private key key=new key();

    public List<User> findAll(int role) {
    String sql = "SELECT ud.id, ud.name, p.permission, ud.age,ud.idcard, ud.tel, ud.email, ud.gender,ud.address, u.account, u.password FROM user_dict ud LEFT JOIN permissions p ON ud.id = p.id LEFT JOIN users u ON ud.id = u.id";
//        String sql = "SELECT id,name,age,tel,idcard,gender,address,email FROM user_dict";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setTel(rs.getString("tel"));
            user.setIdcard(rs.getString("idcard"));
            user.setAge(String.valueOf(rs.getInt("age")));
            user.setGender(rs.getString("gender"));
            user.setAddress(rs.getString("address"));
            user.setRole(rs.getString("permission"));
            String account =rs.getString("account");
            if(account!=null){
                user.setAccount(account);
                try {
                    user.setPassword(rsaKeyManager.decrypt1(key.getPrivate_key(), rs.getString("password")));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return user;
        });

        return maskUserData(users, role);
    }

    public List<User> maskUserData(List<User> users, int role) {
        if (role <= 2&& role>0) {
            return users;
        }

        return users.stream()
                .map(user -> maskUser(user,role))
                .collect(Collectors.toList());
    }

    private User maskUser(User user,int role) {
        User maskedUser = new User();
        // 复制基本属性
        maskedUser.setId(user.getId());
        maskedUser.setAge(user.getAge());
        maskedUser.setGender(user.getGender());
        maskedUser.setAddress(user.getAddress());
        DataMaskUtil dataMaskUtil = new DataMaskUtil(role);


        // 进行脱敏处理
        maskedUser.setUsername(dataMaskUtil.maskName(user.getUsername()));
        maskedUser.setEmail(dataMaskUtil.maskEmail(user.getEmail()));
        maskedUser.setTel(dataMaskUtil.maskPhone(user.getTel()));
        maskedUser.setIdcard(dataMaskUtil.maskIdCard(user.getIdcard()));
        maskedUser.setAddress(dataMaskUtil.maskAddress(user.getAddress()));

        return maskedUser;
    }

    public User findByID(Long id) {
        String sql = "SELECT ud.id, ud.name, ud.email, ud.age, ud.tel,ud.gender, ud.idcard,ud.address,p.permission as role, u.account, u.password FROM user_dict ud LEFT JOIN permissions p ON ud.id = p.id LEFT JOIN users u ON ud.id = u.id WHERE ud.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("name"));
            user.setAge(String.valueOf(rs.getInt("age")));
            user.setTel(rs.getString("tel"));
            user.setIdcard(rs.getString("idcard"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setGender(rs.getString("gender"));
            String account =rs.getString("account");
            user.setAddress(rs.getString("address"));
            if(account!=null){
                user.setAccount(account);
                try {
                    user.setPassword(rsaKeyManager.decrypt1(key.getPrivate_key(), rs.getString("password")));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            return user;
        });
    }

    public void save(User user) {
        String sql = "INSERT INTO user_dict (id,name, email, age, tel,gender,idcard,address) VALUES (?,?, ?, ?, ?, ?, ?, ?)";

        String sql2 = "INSERT INTO permissions (id, permission) VALUES (?, ?)";
        String sql1 = "INSERT INTO users (id, account, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,user.getId(), user.getUsername(), user.getEmail(), user.getAge(), user.getTel(), user.getGender(), user.getIdcard(), user.getAddress());
        jdbcTemplate.update(sql2, user.getId(), user.getRole());

        if(user.getAccount().isEmpty() || user.getPassword().isEmpty()){
            return;
        }
        try {
            jdbcTemplate.update(sql1, user.getId(), user.getAccount(), rsaKeyManager.encrypt(key.getPublic_key(), user.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}