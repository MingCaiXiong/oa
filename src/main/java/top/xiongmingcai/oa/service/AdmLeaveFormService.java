package top.xiongmingcai.oa.service;

import top.xiongmingcai.oa.dao.AdmEmployeeDao;
import top.xiongmingcai.oa.dao.AdmLeaveFormDao;
import top.xiongmingcai.oa.dao.AdmProcessFlowDao;
import top.xiongmingcai.oa.dao.SysNoticeDao;
import top.xiongmingcai.oa.entity.AdmEmployee;
import top.xiongmingcai.oa.entity.AdmLeaveForm;
import top.xiongmingcai.oa.entity.AdmProcessFlow;
import top.xiongmingcai.oa.entity.SysNotice;
import top.xiongmingcai.oa.service.exception.BusinessException;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.text.SimpleDateFormat;
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
    /**
     * 创建请假单
     *
     * @param form 前端输入的请假单数据
     * @return 持久化后的请假单对象
     */
    public AdmLeaveForm createLeaveForm(AdmLeaveForm form) {
        AdmLeaveForm savedForm = (AdmLeaveForm) MyBatisUtils.executrUpdate(sqlSession -> {
            //1.持久化form表单数据,8级以下员工表单状态为processing,8级(总经理)状态为approved
            AdmEmployeeDao employeeDao = sqlSession.getMapper(AdmEmployeeDao.class);
            AdmEmployee employee = employeeDao.queryById(form.getEmployeeId());
            if (employee.getLevel() == 8) {
                form.setState("approved");
            } else {
                form.setState("processing");
            }
            form.setCreateTime(new Date());
            AdmLeaveFormDao leaveFormDao = sqlSession.getMapper(AdmLeaveFormDao.class);
            leaveFormDao.insert(form);
            //2.增加第一条流程数据,说明表单已提交,状态为complete
            AdmProcessFlowDao processFlowDao = sqlSession.getMapper(AdmProcessFlowDao.class);
            AdmProcessFlow flow1 = new AdmProcessFlow();
            flow1.setFormId(form.getFormId());
            flow1.setOperatorId(employee.getEmployeeId());
            flow1.setAction("apply");
            flow1.setCreateTime(new Date());
            flow1.setOrderNo(1);
            flow1.setState("complete");
            flow1.setIsLast(0);
            processFlowDao.insert(flow1);
            //3.分情况创建其余流程数据
            //3.1 7级以下员工,生成部门经理审批任务,请假时间大于72小时,还需生成总经理审批任务
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");
            SysNoticeDao noticeDao = sqlSession.getMapper(SysNoticeDao.class);
            if (employee.getLevel() < 7) {
                AdmEmployee dmanager = employeeDao.queryleader(employee);
                AdmProcessFlow flow2 = new AdmProcessFlow();
                flow2.setFormId(form.getFormId());
                flow2.setOperatorId(dmanager.getEmployeeId());
                flow2.setAction("audit");
                flow2.setCreateTime(new Date());
                flow2.setOrderNo(2);
                flow2.setState("process");
                long diff = form.getEndTime().getTime() - form.getStartTime().getTime();
                float hours = diff / (1000 * 60 * 60) * 1f;
                if (hours >= BusinessConstants.GeneralManagerLeaveApprovalTimeThreshold) {
                    flow2.setIsLast(0);
                    processFlowDao.insert(flow2);
                    AdmEmployee manager = employeeDao.queryleader(dmanager);
                    AdmProcessFlow flow3 = new AdmProcessFlow();
                    flow3.setFormId(form.getFormId());
                    flow3.setOperatorId(manager.getEmployeeId());
                    flow3.setAction("audit");
                    flow3.setCreateTime(new Date());
                    flow3.setState("ready");
                    flow3.setOrderNo(3);
                    flow3.setIsLast(1);
                    processFlowDao.insert(flow3);
                } else {
                    flow2.setIsLast(1);
                    processFlowDao.insert(flow2);
                }
                //请假单已提交消息
                String noticeContent = String.format("您的请假申请[%s-%s]已提交,请等待上级审批."
                        , sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
                noticeDao.insert(new SysNotice(employee.getEmployeeId(), noticeContent));

                //通知部门经理审批消息
                noticeContent = String.format("%s-%s提起请假申请[%s-%s],请尽快审批",
                        employee.getTitle(), employee.getName(), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
                noticeDao.insert(new SysNotice(dmanager.getEmployeeId(), noticeContent));
            } else if (employee.getLevel() == 7) { //部门经理
                //3.2 7级员工,生成总经理审批任务
                AdmEmployee manager = employeeDao.queryleader(employee);
                AdmProcessFlow flow = new AdmProcessFlow();
                flow.setFormId(form.getFormId());
                flow.setOperatorId(manager.getEmployeeId());
                flow.setAction("audit");
                flow.setCreateTime(new Date());
                flow.setState("process");
                flow.setOrderNo(2);
                flow.setIsLast(1);
                processFlowDao.insert(flow);
                //请假单已提交消息
                String noticeContent = String.format("您的请假申请[%s-%s]已提交,请等待上级审批."
                        , sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
                noticeDao.insert(new SysNotice(employee.getEmployeeId(), noticeContent));

                //通知总经理审批消息
                noticeContent = String.format("%s-%s提起请假申请[%s-%s],请尽快审批",
                        employee.getTitle(), employee.getName(), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
                noticeDao.insert(new SysNotice(manager.getEmployeeId(), noticeContent));

            } else if (employee.getLevel() == 8) {
                //3.3 8级员工,生成总经理审批任务,系统自动通过
                AdmProcessFlow flow = new AdmProcessFlow();
                flow.setFormId(form.getFormId());
                flow.setOperatorId(employee.getEmployeeId());
                flow.setAction("audit");
                flow.setResult("approved");
                flow.setReason("自动通过");
                flow.setCreateTime(new Date());
                flow.setAuditTime(new Date());
                flow.setState("complete");
                flow.setOrderNo(2);
                flow.setIsLast(1);
                processFlowDao.insert(flow);
                String noticeContent = String.format("您的请假申请[%s-%s]系统已自动批准通过."
                        , sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
                noticeDao.insert(new SysNotice(employee.getEmployeeId(), noticeContent));
            }
            return form;

        });
        return savedForm;
    }

    /**
     * 获取指定任务状态及指定经办人对应的请假单列表
     *
     * @param pfState    ProcessFlow任务状态
     * @param operatorId 经办人编号
     * @return 请假单及相关数据列表
     */
    public List<Map> getLeaveFormList(String pfState, Long operatorId) {
        return (List<Map>) MyBatisUtils.executrQuery(sqlSession -> {
            AdmLeaveFormDao dao = sqlSession.getMapper(AdmLeaveFormDao.class);
            List<Map> formList = dao.queryProcessByState(pfState, operatorId);
            return formList;
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
            AdmProcessFlowDao processFlowsDao = sqlSession.getMapper(AdmProcessFlowDao.class);
            AdmProcessFlow query = new AdmProcessFlow();
            query.setFormId(formId);
            List<AdmProcessFlow> allList = processFlowsDao.queryAll(query);

            if (allList.size() == 0) {
                throw new BusinessException("PF001", "无效的审批流程");
            }
            //获取任务状态为 process 流程节点
            List<AdmProcessFlow> processList = allList
                    .stream()
                    .filter(p -> p.getOperatorId().equals(operatorId) && p.getState().equals("process"))
                    .collect(Collectors.toList());

            AdmProcessFlow process;
            if (processList.size() == 0) {
                throw new BusinessException("PF002", "未找到待处理的任务");
            } else {
                process = processList.get(0);
                process.setState("complete");
                process.setResult(presult);
                process.setReason(reason);
                process.setAuditTime(new Date());
                processFlowsDao.update(process);
            }


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");
            AdmLeaveFormDao leaveFormDao = sqlSession.getMapper(AdmLeaveFormDao.class);
            AdmLeaveForm form = leaveFormDao.queryById(formId);
            AdmEmployeeDao employeeDao = sqlSession.getMapper(AdmEmployeeDao.class);
            AdmEmployee employee = employeeDao.queryById(form.getEmployeeId());//表单提交人信息
            AdmEmployee operator = employeeDao.queryById(operatorId);//任务经办人信息
            SysNoticeDao noticeDao = sqlSession.getMapper(SysNoticeDao.class);
//2.如果当前任务是最后一个节点,代表流程结束,更新请假单状态为对应的approved/refused
            if(process.getIsLast() == 1){
                form.setState(presult);//approved|refused
                leaveFormDao.update(form);

                String strResult = null;
                if(presult.equals("approved")){
                    strResult = "批准";
                }else if (presult.equals("refused")){
                    strResult = "驳回";
                }
                String noticeContent = String.format("您的请假申请[%s-%s]%s%s已%s,审批意见:%s,审批流程已结束",
                        sdf.format(form.getStartTime()) , sdf.format(form.getEndTime()),
                        operator.getTitle(),operator.getName(),
                        strResult,reason);//发给表单提交人的通知
                noticeDao.insert(new SysNotice(form.getEmployeeId(),noticeContent));

                noticeContent = String.format("%s-%s提起请假申请[%s-%s]您已%s,审批意见:%s,审批流程已结束" ,
                        employee.getTitle() , employee.getName() , sdf.format( form.getStartTime()) , sdf.format(form.getEndTime()),
                        strResult , reason);//发给审批人的通知
                noticeDao.insert(new SysNotice(operator.getEmployeeId(),noticeContent));
            }else{
                //readyList包含所有后续任务节点
                List<AdmProcessFlow> readyList = allList.stream().filter(p -> p.getState().equals("ready")).collect(Collectors.toList());
                //3.如果当前任务不是最后一个节点且审批通过,那下一个节点的状态从ready变为process
                if(presult.equals("approved")){
                    AdmProcessFlow readyProcess = readyList.get(0);
                    readyProcess.setState("process");
                    processFlowsDao.update(readyProcess);
                    //消息1: 通知表单提交人,部门经理已经审批通过,交由上级继续审批
                    String noticeContent1 = String.format("您的请假申请[%s-%s]%s%s已批准,审批意见:%s ,请继续等待上级审批" ,
                            sdf.format(form.getStartTime()) , sdf.format(form.getEndTime()),
                            operator.getTitle() , operator.getName(),reason);
                    noticeDao.insert(new SysNotice(form.getEmployeeId(),noticeContent1));

                    //消息2: 通知总经理有新的审批任务
                    String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s],请尽快审批" ,
                            employee.getTitle() , employee.getName() , sdf.format( form.getStartTime()) , sdf.format(form.getEndTime()));
                    noticeDao.insert(new SysNotice(readyProcess.getOperatorId(),noticeContent2));

                    //消息3: 通知部门经理(当前经办人),员工的申请单你已批准,交由上级继续审批
                    String noticeContent3 = String.format("%s-%s提起请假申请[%s-%s]您已批准,审批意见:%s,申请转至上级领导继续审批" ,
                            employee.getTitle() , employee.getName() , sdf.format( form.getStartTime()) , sdf.format(form.getEndTime()), reason);
                    noticeDao.insert(new SysNotice(operator.getEmployeeId(),noticeContent3));

                }else if(presult.equals("refused")) {
                    //4.如果当前任务不是最后一个节点且审批驳回,则后续所有任务状态变为cancel,请假单状态变为refused
                    for(AdmProcessFlow p:readyList){
                        p.setState("cancel");
                        processFlowsDao.update(p);
                    }
                    form.setState("refused");
                    leaveFormDao.update(form);
                    //消息1: 通知申请人表单已被驳回
                    String noticeContent1 = String.format("您的请假申请[%s-%s]%s%s已驳回,审批意见:%s,审批流程已结束" ,
                            sdf.format(form.getStartTime()) , sdf.format(form.getEndTime()),
                            operator.getTitle() , operator.getName(),reason);
                    noticeDao.insert(new SysNotice(form.getEmployeeId(),noticeContent1));

                    //消息2: 通知经办人表单"您已驳回"
                    String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s]您已驳回,审批意见:%s,审批流程已结束" ,
                            employee.getTitle() , employee.getName() , sdf.format( form.getStartTime()) , sdf.format(form.getEndTime()), reason);
                    noticeDao.insert(new SysNotice(operator.getEmployeeId(),noticeContent2));


                }
            }
            return null;
        });

    }
}