package top.xiongmingcai.oa.dao;

import junit.framework.TestCase;
import top.xiongmingcai.oa.entity.AdmEmployee;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.util.List;

public class AdmEmployeeDaoTest extends TestCase {

    public void testQueryById() {
        AdmEmployee employee = (AdmEmployee) MyBatisUtils.executrQuery(sqlSession -> sqlSession.getMapper(AdmEmployeeDao.class).queryById(1L));
        System.out.println("employee = " + employee);
    }

    public void testQueryAllByLimit() {
    }

    public void testQueryAll() {
        AdmEmployee admEmployee = new AdmEmployee();
        admEmployee.setLevel(3);
        List<AdmEmployee>  employee = (List<AdmEmployee>) MyBatisUtils.executrQuery(sqlSession -> sqlSession.getMapper(AdmEmployeeDao.class).queryAll(admEmployee));
        System.out.println("employee = " + employee);
    }

    public void testInsert() {
    }

    public void testUpdate() {
    }

    public void testDeleteById() {
    }

    public void testQueryLeader() {
        AdmEmployee employee = (AdmEmployee) MyBatisUtils.executrQuery(sqlSession -> sqlSession.getMapper(AdmEmployeeDao.class).queryById(3L));
        System.out.println("employee = " + employee);
        AdmEmployee superiors = (AdmEmployee) MyBatisUtils.executrQuery(sqlSession -> sqlSession.getMapper(AdmEmployeeDao.class).queryleader(employee));
        System.out.println("superiors = " + superiors);
    }
}