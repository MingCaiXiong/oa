package top.xiongmingcai.oa.service;

import top.xiongmingcai.oa.dao.AdmEmployeeDao;
import top.xiongmingcai.oa.entity.AdmEmployee;
import top.xiongmingcai.oa.utils.MyBatisUtils;

/**
 * (AdmEmployee)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 17:39:19
 */
public class AdmEmployeeService {

    /**
     * 通过ID查询单条数据
     *
     * @param employeeId 主键
     * @return 实例对象
     */
    public AdmEmployee queryById(Long employeeId) {
        return (AdmEmployee) MyBatisUtils.executrQuery(sqlSession -> sqlSession.getMapper(AdmEmployeeDao.class).queryById(employeeId));
    }


}