package top.xiongmingcai.oa.service;

import top.xiongmingcai.oa.dao.RbacDao;
import top.xiongmingcai.oa.dao.UserDao;
import top.xiongmingcai.oa.entity.Node;
import top.xiongmingcai.oa.entity.User;
import top.xiongmingcai.oa.service.exception.BusinessException;

import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();
    private RbacDao rbacDao = new RbacDao();

    /**
     * 根据前台输入进行校验
     *
     * @param username 用户名
     * @param password 密码
     * @return 校验通过后，包含对应用户数据的user实体类
     * @throws BusinessException L001-用户名不存在 L002-密码输入错误
     */

    public User checkLogin(String username, String password) {
        // 按照用户名查询用户
        User user = userDao.selectByUsername(username);
        if (user == null) {
            //抛出用户不存在异常
            throw new BusinessException("L001", "用户名为空");
            // 密码输入错误校验
        } else if (!password.equals(user.getPassword())) {
            throw new BusinessException("L002", "密码错误");
        }
        return user;
    }

    public List<Node> selectNodeByUserId(Long userId) {
       return rbacDao.selectByUsername(userId);
    }
}