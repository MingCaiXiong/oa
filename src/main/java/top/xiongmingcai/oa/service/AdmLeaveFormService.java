package top.xiongmingcai.oa.service;

import top.xiongmingcai.oa.dao.AdmEmployeeDao;
import top.xiongmingcai.oa.dao.AdmLeaveFormDao;
import top.xiongmingcai.oa.dao.AdmProcessFlowDao;
import top.xiongmingcai.oa.entity.AdmEmployee;
import top.xiongmingcai.oa.entity.AdmLeaveForm;
import top.xiongmingcai.oa.entity.AdmProcessFlow;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.util.Date;

/**
 * (AdmLeaveForm)表服务
 *
 * @author makejava
 * @since 2021-04-07 17:05:24
 */
public class AdmLeaveFormService {
//    ready-准备 process-正在处理 complete-处理完成 cancel-取消
    private String  准备 = "ready";
    private String  正在处理 = "process";
    private String  处理完成 = "complete";
    private String  取消 = "cancel";
    private String  审批通过 = "approved";
    // apply-申请  audit-审批
    private String  申请 = "apply";
    private String  审批 = "audit";
    private final Integer integer =  8;
    private final Integer 部门经理 =  7;


    public AdmLeaveForm createLeaveForm(AdmLeaveForm from) {
        AdmLeaveForm admLeaveForm=(AdmLeaveForm)MyBatisUtils.executrUpdate(sqlSession -> {
            //1)  持久化form表单数据，8级以下员工表单状态为 processing，8级（总经理）状态为 approved
            AdmEmployeeDao employeeDao = sqlSession.getMapper(AdmEmployeeDao.class);
            AdmEmployee employee = employeeDao.queryById(from.getEmployeeId());
            Integer level = employee.getLevel();
            if (level == integer) {
                from.setState(审批通过);
            } else {
                from.setState(正在处理);
            }

            AdmLeaveFormDao leaveFormDao = sqlSession.getMapper(AdmLeaveFormDao.class);
            leaveFormDao.insert(from);
            // 2) 增加第一条流程数据，说明表单已提交，状态为 complete
            AdmProcessFlowDao processFlowDao = sqlSession.getMapper(AdmProcessFlowDao.class);
            AdmProcessFlow 任务1 = new AdmProcessFlow();
            任务1.setFormId(from.getFormId());
            任务1.setOperatorId(employee.getEmployeeId());
            任务1.setAction(申请);
            任务1.setCreateTime(new Date());
            int 任务序号 =1;
            任务1.setOrderNo(任务序号);
            任务1.setState(处理完成);
            任务1.setIsLast(0);
            processFlowDao.insert(任务1);
            // 3.分情况创建其余流程数据
            //3.1)  7级以下员工，生成部门经理审批任务，请假时间大于36小时，还需生成总经理审批任务
            if ( level < 部门经理) {
                AdmEmployee 部门经理 = employeeDao.queryleader(employee); //获得上级领导
                AdmProcessFlow 任务2 = new AdmProcessFlow();
                任务2.setFormId(from.getFormId());
                任务2.setOperatorId(部门经理.getEmployeeId());//经办人
                任务2.setAction(审批);
                任务2.setCreateTime(new Date());
                任务2.setOrderNo(2);
                任务2.setState(正在处理);//正在处理
                Date endTime = from.getEndTime();
                Date startTime = from.getStartTime();
                long diff = endTime.getTime() - startTime.getTime();
                float 请假时间 = (diff / (60 * 60 * 1000)) * 1f;

                if (请假时间 >= BusinessConstants.总经理请假审批时间阀值) {
                    任务2.setIsLast(0);
                    processFlowDao.insert(任务2);
                    //处理总经理审批逻辑
                    AdmEmployee 总经理 = employeeDao.queryleader(部门经理);
                    AdmProcessFlow 任务3 = new AdmProcessFlow();
                    任务3.setFormId(from.getFormId());
                    任务3.setOperatorId(总经理.getEmployeeId());
                    任务3.setAction(申请);
                    任务3.setCreateTime(new Date());
                    任务3.setOrderNo(1);
                    任务3.setState(准备);
                    任务3.setIsLast(1);//是否最后节点,0-否 1-是
                    processFlowDao.insert(任务3);

                }else {
                    任务2.setIsLast(0);
                    processFlowDao.insert(任务2);
                }

            }else if (level == 部门经理){
                //3.2)  7级员工，生成总经理审批任务
                AdmEmployee 总经理 = employeeDao.queryleader(employee);
                AdmProcessFlow flow = new AdmProcessFlow();
                flow.setFormId(from.getFormId());
                flow.setOperatorId(总经理.getEmployeeId());
                flow.setAction(审批);
                flow.setCreateTime(new Date());
                flow.setState(正在处理);
                flow.setOrderNo(2);
                flow.setIsLast(1);
                processFlowDao.insert(flow);
            }else if (level == integer){
                //3.3)  8级员工，生成总经理审批任务，系统自动通过
                AdmProcessFlow flow = new AdmProcessFlow();
                flow.setOperatorId(employee.getEmployeeId());
                flow.setAction(审批);
                flow.setReason(审批通过);
                flow.setReason("自动通过");
                flow.setCreateTime(new Date());
                flow.setAuditTime(new Date());
                flow.setState(处理完成);
                flow.setOrderNo(2);
                flow.setIsLast(1);
                processFlowDao.insert(flow);
            }


            return from;
        });

        return admLeaveForm;
    }

}