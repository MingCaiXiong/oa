package top.xiongmingcai.oa.dao;

import top.xiongmingcai.oa.entity.Node;
import top.xiongmingcai.oa.entity.User;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.util.List;

public class RbacDao {
    /**
     *
     * @param userid
     * @return
     */
    public List<Node> selectByUsername(Long userid) {
        return ( List<Node>) MyBatisUtils.executrQuery(sqlSession -> sqlSession.selectList("nodemapper.selectNodeByUserId", userid));
    }
}
