package com.changhf.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Alice
 *
 * @Date:2016年11月7日
 */
public class UserDepartment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1891339829785110440L;
    /**
     * 部门ID
     */
    private Integer departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 父部门ID
     */
    private Integer departmentParentid;
    /**
     * 部门级别 1，2，3
     */
    private Integer departmentDepth;
    /**
     * 企业ID
     */
    private Integer enterpriseId;
    /**
     * 部门状态
     */
    private Integer status;
    /**
     * 创建记录时间
     */
    private Date createTime;
    /**
     * 记录最近一次修改时间
     */
    private Date updateTime;

    public UserDepartment() {
    }

    public UserDepartment(String departmentName, Integer departmentParentid, Integer departmentDepth, Integer enterpriseId, Integer status) {
        Date date = new Date();
        this.departmentName = departmentName;
        this.departmentParentid = departmentParentid;
        this.departmentDepth = departmentDepth;
        this.enterpriseId = enterpriseId;
        this.status = status;
        this.createTime = date;
        this.updateTime = date;
    }

    public UserDepartment(Integer departmentId, String departmentName, Integer departmentParentid, Integer status) {
        Date date = new Date();
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentParentid = departmentParentid;
        this.status = status;
        this.createTime = date;
        this.updateTime = date;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public Integer getDepartmentParentid() {
        return departmentParentid;
    }

    public void setDepartmentParentid(Integer departmentParentid) {
        this.departmentParentid = departmentParentid;
    }

    public Integer getDepartmentDepth() {
        return departmentDepth;
    }

    public void setDepartmentDepth(Integer departmentDepth) {
        this.departmentDepth = departmentDepth;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
