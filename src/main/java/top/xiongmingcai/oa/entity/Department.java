package top.xiongmingcai.oa.entity;

import java.io.Serializable;

/**
 * (Department)实体类
 *
 * @author makejava
 * @since 2021-04-07 13:41:19
 */
public class Department implements Serializable {
    private static final long serialVersionUID = -37138849425585957L;
    
    private Long departmentId;
    
    private String departmentName;


    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}