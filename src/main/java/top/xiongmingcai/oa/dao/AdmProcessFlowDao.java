package top.xiongmingcai.oa.dao;

import top.xiongmingcai.oa.entity.AdmProcessFlow;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (AdmProcessFlow)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-08 13:42:46
 */
public interface AdmProcessFlowDao {

    /**
     * 通过ID查询单条数据
     *
     * @param processId 主键
     * @return 实例对象
     */
    AdmProcessFlow queryById(Long processId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AdmProcessFlow> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param admProcessFlow 实例对象
     * @return 对象列表
     */
    List<AdmProcessFlow> queryAll(AdmProcessFlow admProcessFlow);

    /**
     * 新增数据
     *
     * @param admProcessFlow 实例对象
     * @return 影响行数
     */
    int insert(AdmProcessFlow admProcessFlow);

    /**
     * 修改数据
     *
     * @param admProcessFlow 实例对象
     * @return 影响行数
     */
    int update(AdmProcessFlow admProcessFlow);

    /**
     * 通过主键删除数据
     *
     * @param processId 主键
     * @return 影响行数
     */
    int deleteById(Long processId);

    List<AdmProcessFlow> selectByFormId(Long formId);

}