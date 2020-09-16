package lzy.special.special.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

public class Md5Util {
    @Value("${jwt.base64-secret}")
    private static String base64Secret;
    /**
     * 验证码加密工具
     * @param captcha
     * @return
     */
    public static String md5AsCaptcha(String captcha,String time){
        return DigestUtils.md5DigestAsHex((base64Secret+captcha+time).getBytes()) +"+"+ time;
    }
}
