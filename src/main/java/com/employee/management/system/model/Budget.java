package com.employee.management.system.model;

import com.employee.management.system.entity.Project;

public class Budget {

    private Long deptId;

    private String deptName;

    private String budget;

    private int noOfProjects;

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

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public int getNoOfProjects() {
        return noOfProjects;
    }

    public void setNoOfProjects(int noOfProjects) {
        this.noOfProjects = noOfProjects;
    }

    public Budget(Long deptId, String deptName, String budget, int noOfProjects) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.budget = budget;
        this.noOfProjects = noOfProjects;
    }

    public Budget() {
    }
}
