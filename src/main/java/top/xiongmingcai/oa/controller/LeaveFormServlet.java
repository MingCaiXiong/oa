package top.xiongmingcai.oa.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xiongmingcai.oa.entity.AdmLeaveForm;
import top.xiongmingcai.oa.entity.User;
import top.xiongmingcai.oa.service.AdmLeaveFormService;

import javax.management.RuntimeErrorException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "LeaveFormServlet", value = "/leave/*")
public class LeaveFormServlet extends HttpServlet {
    private AdmLeaveFormService leaveFormService = new AdmLeaveFormService();
    private Logger logger = LoggerFactory.getLogger(LeaveFormServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        String url = String.valueOf(request.getRequestURL());
        String methodName = url.substring(url.lastIndexOf("/") + 1);
        if (methodName.equals("create")) {
            this.create(request, response);
        } else if (methodName.equals("list")) {
            this.getLeaveForList(request, response);
        } else if (methodName.equals("audit")) {
            this.audit(request, response);

        }

    }

    private void audit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formId = request.getParameter("formId");
        String result = request.getParameter("result");
        String reason = request.getParameter("reason");
        Map<String, String> mapResult = new HashMap<>();

        try {
            User login_user = (User) request.getSession().getAttribute("login_user");
            Long employeeId = login_user.getEmployeeId();
            long l = Long.parseLong(formId);
            leaveFormService.audit(l, employeeId, result, reason);
            mapResult.put("code", "0");
            mapResult.put("message", "success");

        } catch (Exception e) {
            logger.error("请假审批异常", e);
            mapResult.put("code", e.getClass().getSimpleName());
            mapResult.put("message", e.getMessage());
        }
        String json = JSON.toJSONString(mapResult);
        // 将json字符串向客户端返回
        response.getWriter().println(json);
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User login_user = (User) session.getAttribute("login_user");

        String form_type = request.getParameter("formType");
        String strstart_time = request.getParameter("startTime");
        String strend_time = request.getParameter("endTime");
        String reason = request.getParameter("reason");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");

        Map<String, Object> result = new HashMap<>();
        try {
            AdmLeaveForm form = new AdmLeaveForm();
            form.setEmployeeId(login_user.getEmployeeId());
            form.setFormType(Integer.valueOf(form_type));
            form.setStartTime(dateFormat.parse(strstart_time));
            form.setEndTime(dateFormat.parse(strend_time));
            form.setReason(reason);
            leaveFormService.createLeaveForm(form);

            result.put("code", "0");
            result.put("message", "成功");

        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("请假申请异常", e);

            result.put("code", e.getClass().getSimpleName());
            result.put("message", e.getMessage());
        }
        String json = JSON.toJSONString(result);
        // 将json字符串向客户端返回
        response.getWriter().println(json);
    }

    private void getLeaveForList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User login_user = (User) request.getSession().getAttribute("login_user");
        List<Map> formList = leaveFormService.getLeaveFormList("process", login_user.getEmployeeId());
        Map<String, Object> result = new HashMap<>();
        result.put("code", "0");
        result.put("message", "");
        result.put("count", formList.size());
        result.put("data", formList);
        String json = JSON.toJSONString(result);
        response.getWriter().println(json);

    }
}
