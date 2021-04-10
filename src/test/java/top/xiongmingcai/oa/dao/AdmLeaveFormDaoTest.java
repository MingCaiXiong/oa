package top.xiongmingcai.oa.dao;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import top.xiongmingcai.oa.entity.AdmLeaveForm;
import top.xiongmingcai.oa.utils.MyBatisUtils;


import java.util.*;

public class AdmLeaveFormDaoTest extends TestCase {
    /*
    * [
      {
        "form_id": 35,
        "employee_id": 3,
        "form_type": 4,
        "start_time": "2020-04-01 00:00:00",
        "end_time": "2020-04-30 00:00:00",
        "reason": "回家乡结婚",
        "create_time": "2020-03-28 21:16:39",
        "state": "approved"
      }
    ]*/
    public void testInsert() {

        Integer howManyRowsToUpdate = (int) MyBatisUtils.executrUpdate(sqlSession -> {
            AdmLeaveFormDao mapper = sqlSession.getMapper(AdmLeaveFormDao.class);
            AdmLeaveForm form = new AdmLeaveForm();
            form.setEmployeeId(4L);
            form.setFormType(4);
            Date endTime = null;
            Date stateTime = null;
            Calendar calendar = new GregorianCalendar();

            stateTime = new Date();

            calendar.setTime(stateTime);
            calendar.setTime(stateTime);
            calendar.add(Calendar.DATE, 3);
            endTime = calendar.getTime();

            form.setStartTime(stateTime);
            form.setEndTime(endTime);
            form.setReason("回家收包谷");
            form.setCreateTime(new Date());

            form.setState("processing");

            return mapper.insert(form);
        });
        System.out.println("更新多少行 = " + howManyRowsToUpdate);
    }

    public void testQueryProcessByState() {
        MyBatisUtils.executrQuery(sqlSession -> {
            AdmLeaveFormDao mapper = sqlSession.getMapper(AdmLeaveFormDao.class);
            List<Map> pocoess = mapper.queryProcessByState("process", 2L);
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("pocoess = " + JSON.toJSONString(pocoess));
            System.out.println("---------------------------------------------------------------------------");
            return null;
        });

    }
}