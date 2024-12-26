package org.example.y1.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RSAKeyManager {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String PRIVATE_KEY_PREFIX = "RSA_PRIVATE_KEY:";

    // 生成临时密钥对并返回公钥，同时将私钥存储到 Redis
    public String generateTemporaryKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(512);
            KeyPair keyPair = keyPairGen.generateKeyPair();

            String keyId = UUID.randomUUID().toString(); // 生成唯一标识符
            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

            // 将私钥存储到 Redis，并设置过期时间（例如 5 分钟）
            redisTemplate.opsForValue().set(PRIVATE_KEY_PREFIX + keyId, privateKey, 5, TimeUnit.MINUTES);

            Map<String, String> response = new HashMap<>();
            response.put("keyId", keyId);
            response.put("publicKey", publicKey);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResult=objectMapper.writeValueAsString(response);
            return jsonResult;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate RSA keys", e);
        }
    }

    // 使用 Redis 中的私钥进行解密
    public String decrypt(String keyId, String encryptedData) throws Exception {

        String privateKeyBase64 = (String) redisTemplate.opsForValue().get(PRIVATE_KEY_PREFIX + keyId);

        if (privateKeyBase64 == null) {
            throw new IllegalArgumentException("Invalid or expired keyId");
        }

        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(privateKeyBytes));

        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(decodedData);

        return new String(decryptedData);
    }

    // 使用私钥进行解密
    public String decrypt1(String privateKeyBase64, String encryptedData) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(privateKeyBytes));

        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(decodedData);

        return new String(decryptedData);
    }

    //使用公钥进行加密
    public String encrypt(String publicKeyBase64, String data) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(publicKeyBytes));

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());

        return Base64.getEncoder().encodeToString(encryptedData);
    }



}
