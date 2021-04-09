package top.xiongmingcai.oa.dao;

import top.xiongmingcai.oa.entity.AdmLeaveForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (AdmLeaveForm)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-07 17:05:24
 */
public interface AdmLeaveFormDao {

    /**
     * 通过ID查询单条数据
     *
     * @param formId 主键
     * @return 实例对象
     */
    AdmLeaveForm queryById(Long formId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AdmLeaveForm> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param admLeaveForm 实例对象
     * @return 对象列表
     */
    List<AdmLeaveForm> queryAll(AdmLeaveForm admLeaveForm);

    /**
     * 新增数据
     *
     * @param admLeaveForm 实例对象
     * @return 影响行数
     */
    int insert(AdmLeaveForm admLeaveForm);

    /**
     * 修改数据
     *
     * @param admLeaveForm 实例对象
     * @return 影响行数
     */
    int update(AdmLeaveForm admLeaveForm);

    /**
     * 通过主键删除数据
     *
     * @param formId 主键
     * @return 影响行数
     */
    int deleteById(Long formId);

    /**
     * 按state查询请假条流程数据
     *
     * @param pfState 请假条流程状态
     * @param operatorId 就经办人
     * @return
     */
    List<Map> queryProcessByState(@Param("pf_state") String pfState, @Param("pf_operator_id") Long operatorId);
}