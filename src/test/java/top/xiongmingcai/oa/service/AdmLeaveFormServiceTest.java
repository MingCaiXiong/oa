package top.xiongmingcai.oa.service;

import junit.framework.TestCase;
import org.junit.Test;
import top.xiongmingcai.oa.entity.AdmLeaveForm;

import java.text.SimpleDateFormat;
import java.util.*;

public class AdmLeaveFormServiceTest extends TestCase {
    /**
     * 基层员工请假72 小时测试用例
     */
    public void testCreateLeaveForm() {
        AdmLeaveFormService savedForm = new AdmLeaveFormService();
        Calendar calendar = new GregorianCalendar();

        Date endTime = null;
        Date stateTime = null;


        stateTime = new Date();
        calendar.setTime(stateTime);
        calendar.setTime(stateTime);
        calendar.add(Calendar.DATE, 3);


        endTime = calendar.getTime();

        AdmLeaveForm form = new AdmLeaveForm();
        form.setEmployeeId(10L);
        form.setStartTime(stateTime);
        form.setEndTime(endTime);
        form.setReason("回家收包谷");
        form.setFormType(1);
        form.setReason("市场部员工请假单 72 小时");
        form.setCreateTime(new Date());


        AdmLeaveForm savedFormLeaveForm = savedForm.createLeaveForm(form);
        System.out.println("savedFormLeaveForm.getFormId() = " + savedFormLeaveForm.getFormId());
    }
    /**
     * 基层员工请假24 小时测试用例
     */
    public void testCreateLeaveForm2() {
        AdmLeaveFormService savedForm = new AdmLeaveFormService();
        Calendar calendar = new GregorianCalendar();

        Date endTime = null;
        Date stateTime = null;


        stateTime = new Date();
        calendar.setTime(stateTime);
        calendar.setTime(stateTime);
        calendar.add(Calendar.DATE, 1);


        endTime = calendar.getTime();

        AdmLeaveForm form = new AdmLeaveForm();
        form.setEmployeeId(10L);
        form.setStartTime(stateTime);
        form.setEndTime(endTime);
        form.setReason("回家收包谷");
        form.setFormType(1);
        form.setReason("市场部员工请假单 24 小时");
        form.setCreateTime(new Date());


        AdmLeaveForm savedFormLeaveForm = savedForm.createLeaveForm(form);
        System.out.println("savedFormLeaveForm.getFormId() = " + savedFormLeaveForm.getFormId());
    }
    @Test
    public void testgetLeaveFormList(){
        AdmLeaveFormService savedForm = new AdmLeaveFormService();
        List<Map> leaveFormList = savedForm.getLeaveFormList("process", 6L);
        System.out.println("leaveFormList = " + leaveFormList);
    }

    public void testAudit() {
        AdmLeaveFormService leaveFormService = new AdmLeaveFormService();
        leaveFormService.audit(31L,2L,"approved","祝早日康复!");
    }
}