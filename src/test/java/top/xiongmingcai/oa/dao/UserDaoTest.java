package top.xiongmingcai.oa.dao;

import junit.framework.TestCase;
import top.xiongmingcai.oa.entity.User;

public class UserDaoTest extends TestCase {

    public void testSelectByUsername() {
        User t1 = new UserDao().selectByUsername("m8");
        t1 =t1;
    }
}