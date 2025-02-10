package com.employee.management.system.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
@Table(name="projects")
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pId;

    private String pName;
    private String pDesc;
    private String startDate;
    private String endDate;
    private Double budget;

    @ManyToMany(mappedBy = "projects", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Employee> employeeList;

    @ManyToOne
    private Department department;

    public Project() {
    }

    public Project(long pId, String pName, String pDesc, String startDate, String endDate, Double budget, List<Employee> employeeList, Department department) {
        this.pId = pId;
        this.pName = pName;
        this.pDesc = pDesc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.employeeList = employeeList;
        this.department = department;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}
