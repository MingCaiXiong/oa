package top.xiongmingcai.oa.dao;

import top.xiongmingcai.oa.entity.SysNotice;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SysNotice)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-10 19:11:16
 */
public interface SysNoticeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param noticeId 主键
     * @return 实例对象
     */
    SysNotice queryById(Long noticeId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysNotice> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询指定行数据
     *
     * @param receiverId 通知人id
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysNotice> selectByReceiverId(@Param("receiverId") Long receiverId);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysNotice 实例对象
     * @return 对象列表
     */
    List<SysNotice> queryAll(SysNotice sysNotice);

    /**
     * 新增数据
     *
     * @param sysNotice 实例对象
     * @return 影响行数
     */
    int insert(SysNotice sysNotice);

    /**
     * 修改数据
     *
     * @param sysNotice 实例对象
     * @return 影响行数
     */
    int update(SysNotice sysNotice);

    /**
     * 通过主键删除数据
     *
     * @param noticeId 主键
     * @return 影响行数
     */
    int deleteById(Long noticeId);

}