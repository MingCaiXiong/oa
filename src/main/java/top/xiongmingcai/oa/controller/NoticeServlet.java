package top.xiongmingcai.oa.controller;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.protocol.x.Notice;
import top.xiongmingcai.oa.entity.SysNotice;
import top.xiongmingcai.oa.entity.User;
import top.xiongmingcai.oa.service.SysNoticeService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "NoticeServlet", value = "/notice/list")
public class NoticeServlet extends HttpServlet {
    private  SysNoticeService noticeService = new SysNoticeService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User login_user = (User) request.getSession().getAttribute("login_user");

        List<?> noticeList = noticeService.selectByReceiverId(login_user.getEmployeeId());
        Map result = new HashMap<>();
        result.put("code", "0");
        result.put("msg", "");
        result.put("count", noticeList.size());
        result.put("data", noticeList);
        String json = JSON.toJSONString(result);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
