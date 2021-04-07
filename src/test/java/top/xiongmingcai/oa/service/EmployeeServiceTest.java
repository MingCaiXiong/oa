package top.xiongmingcai.oa.service;

import junit.framework.TestCase;
import top.xiongmingcai.oa.entity.Employee;

public class EmployeeServiceTest extends TestCase {

    public void testQueryById() {
        EmployeeService employeeService = new EmployeeService();
        Employee employee = employeeService.queryById(1L);
        System.out.println("employee = " + employee);
    }
}