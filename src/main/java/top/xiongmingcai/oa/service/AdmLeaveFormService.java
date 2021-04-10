package top.xiongmingcai.oa.service;

import top.xiongmingcai.oa.dao.AdmEmployeeDao;
import top.xiongmingcai.oa.dao.AdmLeaveFormDao;
import top.xiongmingcai.oa.dao.AdmProcessFlowDao;
import top.xiongmingcai.oa.entity.AdmEmployee;
import top.xiongmingcai.oa.entity.AdmLeaveForm;
import top.xiongmingcai.oa.entity.AdmProcessFlow;
import top.xiongmingcai.oa.service.exception.BusinessException;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (AdmLeaveForm)表服务
 *
 * @author makejava
 * @since 2021-04-07 17:05:24
 */
public class AdmLeaveFormService {
    //    ready-准备 process-正在处理 complete-处理完成 cancel-取消
    private String ready = "ready";
    private String process = "process";
    private String complete = "complete";
    private String cancel = "cancel";
    private String approved = "approved";
    // apply-申请  audit-审批
    private String apply = "apply";
    private String audit = "audit";
    private final Integer BOSS = 8;
    private final Integer manager = 7;


    public AdmLeaveForm createLeaveForm(AdmLeaveForm form) {
        AdmLeaveForm admLeaveForm = (AdmLeaveForm) MyBatisUtils.executrUpdate(sqlSession -> {
            //1)  持久化form表单数据，8级以下员工表单状态为 processing，8级（总经理）状态为 approved
            AdmEmployeeDao employeeDao = sqlSession.getMapper(AdmEmployeeDao.class);
            AdmEmployee employee = employeeDao.queryById(form.getEmployeeId());
            Integer level = employee.getLevel();
            if (level == BOSS) {
                form.setState(approved);
            } else {
                form.setState(process);
            }
            form.setCreateTime(new Date());
            AdmLeaveFormDao leaveFormDao = sqlSession.getMapper(AdmLeaveFormDao.class);
            leaveFormDao.insert(form);
            // 2) 增加第一条流程数据，说明表单已提交，状态为 complete
            AdmProcessFlowDao processFlowDao = sqlSession.getMapper(AdmProcessFlowDao.class);
            AdmProcessFlow flow1 = new AdmProcessFlow();
            flow1.setFormId(form.getFormId());
            flow1.setOperatorId(employee.getEmployeeId());
            //请假条:过程 apply-申请  audit-审批
            flow1.setAction(apply);
            flow1.setCreateTime(new Date());
            flow1.setOrderNo(1);
            //请假条状态: ready-准备 process-正在处理 complete-处理完成 cancel-取消
            flow1.setState(complete);
            flow1.setIsLast(0);
            processFlowDao.insert(flow1);
            // 3.分情况创建其余流程数据
            //3.1)  7级以下员工，生成部门经理审批任务，请假时间大于36小时，还需生成总经理审批任务

            if (level < manager) {
                AdmEmployee leadership = employeeDao.queryleader(employee); //获得上级领导
                AdmProcessFlow flow2 = new AdmProcessFlow();
                flow2.setFormId(form.getFormId());
                flow2.setOperatorId(leadership.getEmployeeId());//经办人
                flow2.setAction(audit);
                flow2.setCreateTime(new Date());
                flow2.setOrderNo(2);
                //请假条状态: ready-准备 process-正在处理 complete-处理完成 cancel-取消
                flow2.setState(process);//正在处理
                Date endTime = form.getEndTime();
                Date startTime = form.getStartTime();
                long diff = endTime.getTime() - startTime.getTime();
                float hours = diff / (60 * 60 * 1000) * 1f;

                if (hours < BusinessConstants.GeneralManagerLeaveApprovalTimeThreshold) {
                    //如果请假小于36小时不需要总经理审批
                    //是否最后节点,0-否 1-是
                    flow2.setIsLast(1);//是
                    processFlowDao.insert(flow2);

                } else {
                    //是否最后节点,0-否 1-是
                    flow2.setIsLast(0);// 否 36小时还需要总经理审批
                    processFlowDao.insert(flow2);


                    //处理总经理审批逻辑
                    AdmEmployee boss = employeeDao.queryleader(leadership); //获得上级领导
                    AdmProcessFlow flow3 = new AdmProcessFlow();
                    flow3.setFormId(form.getFormId());
                    flow3.setOperatorId(boss.getEmployeeId());//登记经办人
                    //请假条:过程 apply-申请  audit-审批
                    flow3.setAction(apply);//上级部门经理审批后方可审批
                    flow3.setCreateTime(new Date());
                    //请假条状态: ready-准备 process-正在处理 complete-处理完成 cancel-取消
                    flow3.setState(ready);
                    flow3.setOrderNo(3);
                    flow3.setIsLast(1);//是否最后节点,0-否 1-是
                    processFlowDao.insert(flow3);

                }

            } else if (level == manager) {
                //3.2)  7级员工，生成总经理审批任务
                AdmEmployee generalManager = employeeDao.queryleader(employee);
                AdmProcessFlow flow = new AdmProcessFlow();
                flow.setFormId(form.getFormId());
                flow.setOperatorId(generalManager.getEmployeeId());
                flow.setAction(audit);
                flow.setCreateTime(new Date());
                //请假条状态: ready-准备 process-正在处理 complete-处理完成 cancel-取消
                flow.setState(process);
                flow.setOrderNo(2);//这是任务中第二节点
                flow.setIsLast(1);
                processFlowDao.insert(flow);
            } else if (level == BOSS) {
                //3.3)  8级员工，生成总经理审批任务，系统自动通过
                AdmProcessFlow flow = new AdmProcessFlow();
                flow.setFormId(form.getFormId());
                flow.setOperatorId(employee.getEmployeeId());
                flow.setAction(audit);
                flow.setResult(approved);//审批意见
                flow.setReason("自动通过");
                flow.setCreateTime(new Date());
                flow.setAuditTime(new Date());
                //请假条状态: ready-准备 process-正在处理 complete-处理完成 cancel-取消
                flow.setState(complete);
                flow.setOrderNo(2);
                flow.setIsLast(1);
                processFlowDao.insert(flow);
            }


            return form;
        });

        return admLeaveForm;
    }

    /**
     * 返回需要审批的请假条列表
     *
     * @param pfState    状态
     * @param operatorId 审核人
     * @return
     */
    public List<Map> getLeaveFormList(String pfState, Long operatorId) {
        return (List<Map>) MyBatisUtils.executrQuery(sqlSession -> {
            AdmLeaveFormDao mapper = sqlSession.getMapper(AdmLeaveFormDao.class);
            return mapper.queryProcessByState(pfState, operatorId);
        });
    }

    /**
     * @param formId
     * @param operatorId 经办人Id
     * @param presult    approved-同意 refused-驳回
     * @param reason     审批意见
     */
    public void audit(Long formId, Long operatorId, String presult, String reason) {
//        1.无论同意/驳回,当前任务状态变更为complete
        MyBatisUtils.executrUpdate(sqlSession -> {
            AdmProcessFlowDao ProcessFlowDao = sqlSession.getMapper(AdmProcessFlowDao.class);
            AdmProcessFlow query = new AdmProcessFlow();
            query.setFormId(formId);
            List<AdmProcessFlow> admProcessFlows = ProcessFlowDao.queryAll(query);

            if (admProcessFlows.size() == 0) {
                throw new BusinessException("PF001", "无效的审批流程");
            }
            //获取任务状态为 process 流程节点
            List<AdmProcessFlow> processList = admProcessFlows
                    .stream()
                    .filter(p -> p.getOperatorId().equals(operatorId) && p.getState().equals("process"))
                    .collect(Collectors.toList());

            AdmProcessFlow processFlowIndex;
            if (processList.size() == 0) {
                throw new BusinessException("PF002", "未找到待处理的任务");
            } else {
                processFlowIndex = processList.get(0);
                processFlowIndex.setState("complete");
                processFlowIndex.setResult(reason);
                processFlowIndex.setAuditTime(new Date());
                int update = ProcessFlowDao.update(processFlowIndex);
                System.out.println("更新了多少行 = " + update);
            }
            AdmLeaveFormDao leaveFormDao = sqlSession.getMapper(AdmLeaveFormDao.class);
            AdmLeaveForm leaveForm = leaveFormDao.queryById(formId);
            //2.如果当前任务是最后一个节点,代表流程结束,更新请假单状态为对应的approved/refused
            if (processFlowIndex.getIsLast() == 1) {
//                approved | refused
                leaveForm.setState(presult);
                leaveFormDao.update(leaveForm);
            } else {
                //        3.如果当前任务不是最后一个节点且审批通过,那下一个节点的状态从ready变为process
                List<AdmProcessFlow> readyList = admProcessFlows.stream()
                        .filter(admProcessFlow
                                -> admProcessFlow.getState().equals("ready")).collect(Collectors.toList());
                if (presult.equals(approved)) {
                    AdmProcessFlow readyProcess = readyList.get(0);
                    readyProcess.setState(process);
                    ProcessFlowDao.update(readyProcess);
//        4.如果当前任务不是最后一个节点且审批驳回,则后续所有任务状态变为cancel,请假单状态变为refused
                } else if (presult.equals("refused")) {
                    for (AdmProcessFlow item : readyList) {
                        item.setState("cancel");
                        ProcessFlowDao.update(item);
                    }
                    leaveForm.setState("refused");
                }

            }
            return null;
        });

    }
}