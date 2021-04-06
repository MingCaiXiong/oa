package top.xiongmingcai.oa.service;

import junit.framework.TestCase;
import org.junit.Test;

public class UserServiceTest extends TestCase {

    private UserService service = new UserService();
    @Test
    public void testCheckLogin1() {
        service.checkLogin("1233","1222");
    }
    @Test
    public void testCheckLogin2() {
        service.checkLogin("m8","1222");
    }
    @Test
    public void testCheckLogin3() {
        service.checkLogin("m8","test");
    }
}