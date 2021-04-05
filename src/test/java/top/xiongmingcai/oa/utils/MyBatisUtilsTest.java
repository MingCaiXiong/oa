package top.xiongmingcai.oa.utils;

import junit.framework.TestCase;
import org.junit.Test;

public class MyBatisUtilsTest extends TestCase {

    @Test
    public void testMain() {

        String out = (String)MyBatisUtils.executeQuery(sqlSession ->sqlSession.selectOne("test.sample"));
        System.out.println("out = " + out);
    }
}