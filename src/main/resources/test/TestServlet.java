package top.xiongmingcai.oa.test;

import top.xiongmingcai.oa.utils.MyBatisUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
// 测试代码
//urlPatterns:将地址映射到/test
@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = (String) MyBatisUtils.executrQuery(sqlSession -> sqlSession.selectOne("test.sample"));
        // 在test.ftl文件里，body里面的属性名是result，所以这里第一个参数也是
        // 把sql返回的结果放入到当前请求
        request.setAttribute("result", result);
        // 请求转发
        request.getRequestDispatcher("/test.ftl").forward(request, response);
    }
}