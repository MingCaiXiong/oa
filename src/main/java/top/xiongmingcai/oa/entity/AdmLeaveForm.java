package top.xiongmingcai.oa.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (AdmLeaveForm)实体类
 *
 * @author makejava
 * @since 2021-04-07 17:05:20
 */
public class AdmLeaveForm implements Serializable {
    private static final long serialVersionUID = -64439597749875744L;
    /**
    * 请假单编号
    */
    private Long formId;
    /**
    * 员工编号
    */
    private Long employeeId;
    /**
    * 请假类型 1-事假 2-病假 3-工伤假 4-婚假 5-产假 6-丧假
    */
    private Integer formType;
    /**
    * 请假起始时间
    */
    private Date startTime;
    /**
    * 请假结束时间
    */
    private Date endTime;
    /**
    * 请假事由
    */
    private String reason;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * processing-正在审批 approved-审批已通过 refused-审批被驳回
    */
    private String state;


    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}