package org.example.y1.key;

import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class keymain {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(512);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        String keyId = UUID.randomUUID().toString(); // 生成唯一标识符
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        try (FileWriter publicKeyWriter = new FileWriter("src/main/java/resources/y1/key/public_key.pem");
             FileWriter privateKeyWriter = new FileWriter("src/main/java/resources/y1/key/private_key.pem")) {
            publicKeyWriter.write("-----BEGIN PUBLIC KEY-----\n");
            publicKeyWriter.write(publicKey);
            publicKeyWriter.write("\n-----END PUBLIC KEY-----\n");

            privateKeyWriter.write("-----BEGIN PRIVATE KEY-----\n");
            privateKeyWriter.write(privateKey);
            privateKeyWriter.write("\n-----END PRIVATE KEY-----\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
