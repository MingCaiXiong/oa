package top.xiongmingcai.oa.entity;

import java.io.Serializable;

/**
 * (AdmEmployee)实体类
 *
 * @author makejava
 * @since 2021-04-08 17:39:19
 */
public class AdmEmployee implements Serializable {
    private static final long serialVersionUID = -86923352454701699L;
    
    private Long employeeId;
    
    private String name;
    
    private Long departmentId;
    
    private String title;
    
    private Integer level;


    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

}