package top.xiongmingcai.oa.dao;

import top.xiongmingcai.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (AdmEmployee)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-07 12:19:40
 */
public interface EmployeeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param employeeId 主键
     * @return 实例对象
     */
    public Employee queryById(Long employeeId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
//    List<AdmEmployee> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param admEmployee 实例对象
     * @return 对象列表
     */
//    List<AdmEmployee> queryAll(AdmEmployee admEmployee);

    /**
     * 新增数据
     *
     * @param admEmployee 实例对象
     * @return 影响行数
     */
//    int insert(AdmEmployee admEmployee);

    /**
     * 修改数据
     *
     * @param admEmployee 实例对象
     * @return 影响行数
     */
//    int update(AdmEmployee admEmployee);

    /**
     * 通过主键删除数据
     *
     * @param employeeId 主键
     * @return 影响行数
     */
//    int deleteById(Long employeeId);

}