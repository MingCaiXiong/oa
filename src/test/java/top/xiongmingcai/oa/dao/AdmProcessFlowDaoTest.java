package top.xiongmingcai.oa.dao;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import top.xiongmingcai.oa.entity.AdmProcessFlow;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AdmProcessFlowDaoTest extends TestCase {

    public void testInsert() {

    }

    public void testQueryAll() {
        MyBatisUtils.executrUpdate(sqlSession -> {
            AdmProcessFlowDao processFlowDao = sqlSession.getMapper(AdmProcessFlowDao.class);
            AdmProcessFlow query = new AdmProcessFlow();
            query.setFormId(31L);
            List<AdmProcessFlow> admProcessFlows = processFlowDao.queryAll(query);

            for (AdmProcessFlow flow : admProcessFlows) {
                System.out.println("flow = " + flow);
            }
                      return null;
        });
    }

    public void testSelectByFormId() {
        MyBatisUtils.executrQuery(sqlSession -> {
            AdmProcessFlowDao processFlowDao = sqlSession.getMapper(AdmProcessFlowDao.class);

            List<AdmProcessFlow> processFlowList = processFlowDao.selectByFormId(31L);
            String jsonString = JSON.toJSONString(processFlowList);
            System.out.println("jsonString = " + jsonString);
            return null;
        });
    }
}