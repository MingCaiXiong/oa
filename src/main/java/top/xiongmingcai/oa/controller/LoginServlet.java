package top.xiongmingcai.oa.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xiongmingcai.oa.entity.User;
import top.xiongmingcai.oa.service.UserService;
import top.xiongmingcai.oa.service.exception.BusinessException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 控制器采用servlet完成，主要是接收来自界面的用户输入，调用业务逻辑，返回对应的结果
// urlPatterns映射地址
@WebServlet(name = "LoginServlet", urlPatterns = "/check_login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        Logger logger = LoggerFactory.getLogger(LoginServlet.class);
        // 设置请求响应的格式
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        // 接收用户输入
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, Object> result = new HashMap<>();
        // 调用业务逻辑进行校验
        try {
            // 登录成功返回的数据
            User user = userService.checkLogin(username, password);
            result.put("code", 0);
            result.put("message", "success");
        } catch (BusinessException ex) {
            logger.error(ex.getMassage(), ex);
            result.put("code", ex.getCode());
            result.put("message", ex.getMassage());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            result.put("code", ex.getClass().getSimpleName());
            result.put("message", ex.getMessage());
        }
        String results = JSON.toJSONString(result);
        // 将json字符串向客户端返回
        response.getWriter().println(results);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}