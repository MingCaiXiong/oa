package top.xiongmingcai.oa.dao;

import junit.framework.TestCase;
import top.xiongmingcai.oa.entity.Node;

import java.util.List;

public class RbacDaoTest extends TestCase {

    public void testSelectNodeByUserId() {
        Long userID = 1L;
        RbacDao rbacDao = new RbacDao();
        List<Node> nodes = rbacDao.selectByUsername(userID);
        nodes=nodes;
    }
}