package com.employee.management.system.model;

import com.employee.management.system.entity.Employee;

import java.util.List;

public class ProjectSearch {

    private Long pId;
    private String pName;
    private String pDesc;
    private List<EmployeeSearch> employeeList;

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public List<EmployeeSearch> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeSearch> employeeList) {
        this.employeeList = employeeList;
    }

    public ProjectSearch(Long pId, String pName, String pDesc, List<EmployeeSearch> employeeList) {
        this.pId = pId;
        this.pName = pName;
        this.pDesc = pDesc;
        this.employeeList = employeeList;
    }

    public ProjectSearch() {
    }
}
