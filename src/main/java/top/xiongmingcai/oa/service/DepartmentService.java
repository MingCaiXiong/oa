package top.xiongmingcai.oa.service;

import top.xiongmingcai.oa.dao.DepartmentDao;
import top.xiongmingcai.oa.entity.Department;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.util.List;

/**
 * (Department)表服务接口
 *
 * @author makejava
 * @since 2021-04-07 14:18:18
 */
public class DepartmentService {

    /**
     * 通过ID查询单条数据
     *
     * @param departmentId 主键
     * @return 实例对象
     */
   public Department queryById(Long departmentId){
       return (Department) MyBatisUtils.executrQuery(sqlSession -> {
           DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);
           Department department = mapper.queryById(departmentId);
           return  department;
       });
   }



}