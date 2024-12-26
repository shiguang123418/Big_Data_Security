package org.example.y1.util;
public class DataMaskUtil {
    private int role;
    public DataMaskUtil(int role){
        this.role=role;
    }
    // 姓名脱敏: 保留第一个字，其他用*代替
    public String maskName(String name) {
        if(this.role==0){
            return name;
        }

        return name.substring(0, 1) + "*".repeat(name.length() - 1);
    }

    // 邮箱脱敏: 仅显示第一个字符和@后面的部分
    public String maskEmail(String email) {
        if (this.role==0) {
            return email;
        }

        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return email;
        }
        return email.charAt(0) + "***" + email.substring(atIndex);
    }

    // 手机号脱敏: 保留前三位和后四位
    public String maskPhone(String phone) {
        if (this.role==0) {
            return phone;
        }
        int x=phone.length()-3;

        return phone.substring(0, 3) + "****" + phone.substring(x);
    }

    // 身份证号脱敏: 保留前四位和后四位
    public String maskIdCard(String idCard) {
        if (this.role==0) {
            return idCard;
        }
        if (idCard == null || idCard.length() < 8) {
            return idCard;
        }
        return idCard.substring(0, 4) + "********" + idCard.substring(idCard.length() - 4);
    }

    // 地址脱敏: 保留前四位
    public String maskAddress(String address) {
        if (this.role==0) {
            return address;
        }
        if (address == null || address.length() < 8) {
            return address;
        }
        return address.substring(0, 4) + "********" + address.substring(address.length() - 4);
    }
}
