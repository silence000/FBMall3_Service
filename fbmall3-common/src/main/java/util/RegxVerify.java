package util;

import java.util.Objects;

public class RegxVerify {
    public static boolean checkout(String content, RegxEnum type) {
        String reg = "";
        if (Objects.equals(type, RegxEnum.USERNAME)) { // 用户名: 4-16位字母,数字,汉字,下划线
            reg = "^([a-zA-Z0-9_\\u4e00-\\u9fa5]{4,16})$";
        }
        if (Objects.equals(type, RegxEnum.PASSWORD)) {
            reg = "^[a-zA-Z]\\w{5,17}$"; // 密码: 以字母开头，长度在6~18之间，只能包含字符、数字和下划线
        }
        if (Objects.equals(type, RegxEnum.PID)) { // 身份证
            reg = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        }
        if (Objects.equals(type, RegxEnum.NAME)) { // 姓名
            reg = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d·s]{2,20}$";
        }
        if (Objects.equals(type, RegxEnum.MOBILE)) { // 手机号
            reg = "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        }
        if (Objects.equals(type, RegxEnum.POSTCODE)) { // 邮编
            reg = "^[1-9]\\d{5}$";
        }
        if (Objects.equals(type, RegxEnum.EMAIL)) { // E-mail
            reg = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        }
        // 判断并返回
        if (content.length() != 0) {
            return !content.matches(reg);
        }
        return true;
    }
}
