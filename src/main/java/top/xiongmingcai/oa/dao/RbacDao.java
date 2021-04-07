package top.xiongmingcai.oa.dao;

import top.xiongmingcai.oa.entity.Node;
import top.xiongmingcai.oa.entity.User;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.util.List;

public class RbacDao {
    /**
     * 按用户名查询用户表
     * @param username 用户名
     * @return User对象包含对应的用户信息，null则代表不存在
     */
    public List<Node> selectByUsername(Long userid) {
        return ( List<Node>) MyBatisUtils.executrQuery(sqlSession -> sqlSession.selectList("nodemapper.selectNodeByUserId", userid));
    }
}
