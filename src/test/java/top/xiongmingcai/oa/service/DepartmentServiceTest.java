package top.xiongmingcai.oa.service;

import junit.framework.TestCase;
import top.xiongmingcai.oa.entity.Department;

public class DepartmentServiceTest extends TestCase {

    public void testQueryById() {
        Department department = new DepartmentService().queryById(1L);
        System.out.println("department = " + department);
    }
}