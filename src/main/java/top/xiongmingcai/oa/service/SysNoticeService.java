package top.xiongmingcai.oa.service;

import com.alibaba.fastjson.JSON;
import top.xiongmingcai.oa.dao.SysNoticeDao;
import top.xiongmingcai.oa.entity.SysNotice;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.util.List;

/**
 * (SysNotice)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 12:13:33
 */
public class SysNoticeService {

    /**
     * 通过ID查询单条数据
     *
     * @param noticeId 主键
     * @return 实例对象
     */
    public SysNotice queryById(Long noticeId){
        return null;
    };


    public List<?> selectByReceiverId(Long employeeId){
        return (List<?>)MyBatisUtils.executrQuery(sqlSession -> {
            SysNoticeDao noticeDao = sqlSession.getMapper(SysNoticeDao.class);
        return  noticeDao.selectByReceiverId(1L);
        });
    }
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
//    public List<SysNotice> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysNotice 实例对象
     * @return 实例对象
     */
//    public SysNotice insert(SysNotice sysNotice);

    /**
     * 修改数据
     *
     * @param sysNotice 实例对象
     * @return 实例对象
     */
//    public SysNotice update(SysNotice sysNotice);

    /**
     * 通过主键删除数据
     *
     * @param noticeId 主键
     * @return 是否成功
     */
//    public boolean deleteById(Long noticeId);

}