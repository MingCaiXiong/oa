package top.xiongmingcai.oa.service;

import top.xiongmingcai.oa.dao.EmployeeDao;
import top.xiongmingcai.oa.entity.Employee;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.util.List;

/**
 * (AdmEmployee)表服务接口
 *
 * @author makejava
 * @since 2021-04-07 12:13:16
 */
public class EmployeeService {

    /**
     * 通过ID查询单条数据
     *
     * @param employeeId 主键
     * @return 实例对象
     */
    public Employee queryById(Long employeeId) {
        return (Employee)MyBatisUtils.executrQuery(sqlSession -> {
            EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
            return employeeDao.queryById(employeeId);
        });
    }


}