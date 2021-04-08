package top.xiongmingcai.oa.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (AdmProcessFlow)实体类
 *
 * @author makejava
 * @since 2021-04-08 13:42:45
 */
public class AdmProcessFlow implements Serializable {
    private static final long serialVersionUID = 311209126164420521L;
    /**
    * 处理任务编号
    */
    private Long processId;
    /**
    * 表单编号
    */
    private Long formId;
    /**
    * 经办人编号
    */
    private Long operatorId;
    /**
    * apply-申请  audit-审批
    */
    private String action;
    /**
    * approved-同意 refused-驳回
    */
    private String result;
    /**
    * 审批意见
    */
    private String reason;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 审批时间
    */
    private Date auditTime;
    /**
    * 任务序号
    */
    private Integer orderNo;
    /**
    * ready-准备 process-正在处理 complete-处理完成 cancel-取消
    */
    private String state;
    /**
    * 是否最后节点,0-否 1-是
    */
    private Integer isLast;


    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getIsLast() {
        return isLast;
    }

    public void setIsLast(Integer isLast) {
        this.isLast = isLast;
    }

}