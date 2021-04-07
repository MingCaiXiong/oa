package top.xiongmingcai.oa.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String md5Encrypt(String source) {
        return DigestUtils.md5Hex(source);
    }

    public static String md5Encrypt(String source, Integer salt) {
        char[] ca = source.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            ca[i] = (char) (ca[i] + salt);

        }
        String garble = String.valueOf(ca);
        return DigestUtils.md5Hex(garble);
    }

}
