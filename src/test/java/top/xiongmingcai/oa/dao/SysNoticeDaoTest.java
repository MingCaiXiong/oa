package top.xiongmingcai.oa.dao;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import top.xiongmingcai.oa.entity.SysNotice;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.util.Date;
import java.util.List;


public class SysNoticeDaoTest extends TestCase {

    public void testQueryById() {
/*[
        {
            "notice_id": 19,
                "receiver_id": 1,
                "content": "测试消息",
                "create_time": "2021-04-07 23:38:22"
        }
]*/
        Integer howManyRowsToUpdate = (int) MyBatisUtils.executrUpdate(sqlSession -> {
            SysNoticeDao mapper = sqlSession.getMapper(SysNoticeDao.class);
            SysNotice notice = new SysNotice();
            notice.setCreateTime(new Date());
            notice.setContent("测试消息");
            notice.setReceiverId(1L);
            return mapper.insert(notice);
        });
        System.out.println("更新多少行 = " + howManyRowsToUpdate);
    }

    public void testSelectByReceiverId() {
        MyBatisUtils.executrQuery(sqlSession -> {
            SysNoticeDao noticeDao = sqlSession.getMapper(SysNoticeDao.class);
            List<SysNotice> sysNotices = noticeDao.selectByReceiverId(1L);
            String jsonString = JSON.toJSONString(sysNotices);
            System.out.println("jsonString = " + jsonString);
            return null;
        });
    }
}