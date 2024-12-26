package org.example.y1.key;

import java.io.*;

import ch.qos.logback.classic.Logger;
import org.springframework.core.io.*;


import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;


public class key {
    private String public_key;
    private String private_key;


    public String getPrivate_key() throws Exception {
        String privateKeyPEM = getContent("y1/key/private_key.pem");
        privateKeyPEM =privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        return privateKeyPEM;

    }

    public String getPublic_key() throws Exception {
        String privateKeyPEM = getContent("y1/key/public_key.pem");
        privateKeyPEM =privateKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        return privateKeyPEM;
    }
    public static String getContent(String filePath) {
        String res = "";
        BufferedReader br = null;
        Logger log = null;
        if (StringUtils.isEmpty(filePath)) {
            log.info("文件路径不能为空");
            return res;
        }
        try {
            Resource resource = new ClassPathResource(filePath);

            br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str).append("\n");
            }
            res = sb.toString();
        } catch (Exception e) {
            log.info("读取文件{}时发生异常", filePath);
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

}
