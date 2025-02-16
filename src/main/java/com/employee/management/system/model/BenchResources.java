package com.employee.management.system.model;

public class BenchResources {
    private String empId;
    private String empName;
    private String deptName;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BenchResources(String empId, String empName, String deptName) {
        this.empId = empId;
        this.empName = empName;
        this.deptName = deptName;
    }

    public BenchResources() {
    }
}
