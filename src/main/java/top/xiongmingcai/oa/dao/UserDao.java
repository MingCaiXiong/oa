package top.xiongmingcai.oa.dao;

import top.xiongmingcai.oa.entity.User;
import top.xiongmingcai.oa.utils.MyBatisUtils;

public class UserDao {
    /**
     * 按用户名查询用户表
     * @param username 用户名
     * @return User对象包含对应的用户信息，null则代表不存在
     */
    public User selectByUsername(String username) {
        return (User) MyBatisUtils.executrQuery(sqlSession -> sqlSession.selectOne("user.selectByUsername", username));
    }
}
