package top.xiongmingcai.oa.service;

import top.xiongmingcai.oa.entity.AdmLeaveForm;
import java.util.List;

/**
 * (AdmLeaveForm)表服务接口
 *
 * @author makejava
 * @since 2021-04-07 17:05:24
 */
public interface AdmLeaveFormService {

    /**
     * 通过ID查询单条数据
     *
     * @param formId 主键
     * @return 实例对象
     */
    AdmLeaveForm queryById(Long formId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AdmLeaveForm> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param admLeaveForm 实例对象
     * @return 实例对象
     */
    AdmLeaveForm insert(AdmLeaveForm admLeaveForm);

    /**
     * 修改数据
     *
     * @param admLeaveForm 实例对象
     * @return 实例对象
     */
    AdmLeaveForm update(AdmLeaveForm admLeaveForm);

    /**
     * 通过主键删除数据
     *
     * @param formId 主键
     * @return 是否成功
     */
    boolean deleteById(Long formId);

}