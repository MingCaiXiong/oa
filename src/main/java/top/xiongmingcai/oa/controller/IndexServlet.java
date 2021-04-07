package top.xiongmingcai.oa.controller;

import top.xiongmingcai.oa.entity.Department;
import top.xiongmingcai.oa.entity.Employee;
import top.xiongmingcai.oa.entity.Node;
import top.xiongmingcai.oa.entity.User;
import top.xiongmingcai.oa.service.DepartmentService;
import top.xiongmingcai.oa.service.EmployeeService;
import top.xiongmingcai.oa.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet", urlPatterns = "/index")
public class IndexServlet extends HttpServlet {
    private UserService userService = new UserService();
    private EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("login_user");
        //获取员工信息
        Long employeeId = user.getEmployeeId();
        Employee employee = employeeService.queryById(employeeId);
        //获取员工对应部门
        Long departmentId = employee.getDepartmentId();
        Department department = new DepartmentService().queryById(departmentId);

        //获取登录用户可用模块列表
        List<Node> nodeslist = userService.selectNodeByUserId(user.getUserId());
        request.setAttribute("node_list", nodeslist);
        request.setAttribute("current_employee", employee);
        request.setAttribute("current_department", department);
        request.getRequestDispatcher("/index.ftl").forward(request, response);
    }
}
