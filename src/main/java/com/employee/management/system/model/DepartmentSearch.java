package com.employee.management.system.model;

import java.util.List;

public class DepartmentSearch {
    private Long deptId;

    private String deptName;

    private String deptDesc;
    private List<EmployeeSearch> employeeSearches;


    public DepartmentSearch(Long deptId, String deptName, String deptDesc, List<EmployeeSearch> employeeSearches) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptDesc = deptDesc;
        this.employeeSearches = employeeSearches;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public DepartmentSearch() {
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<EmployeeSearch> getEmployees() {
        return employeeSearches;
    }

    public void setEmployees(List<EmployeeSearch> employeeSearches) {
        this.employeeSearches = employeeSearches;
    }
}
