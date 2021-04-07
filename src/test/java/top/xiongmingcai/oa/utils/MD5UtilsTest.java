package top.xiongmingcai.oa.utils;

import junit.framework.TestCase;

public class MD5UtilsTest extends TestCase {

    public void testMd5Encrypt() {
        Integer salt= 188;
        String encrypt = MD5Utils.md5Encrypt("test",salt);
        System.out.println(encrypt);

    }
}