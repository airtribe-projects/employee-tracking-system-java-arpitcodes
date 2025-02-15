package com.employee.management.system.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="projects")
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pId;


    @NotNull
    private String pName;
    @NotNull
    private String pDesc;
    private Double budget;

    @ManyToMany(mappedBy = "projects", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Employee> employees;

    @ManyToOne(fetch = FetchType.LAZY) // Many projects belong to one department
    @JoinColumn(name = "department_id") // Foreign key column
    @JsonIgnore
    private Department department;


    public Project() {
    }


    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Project(long pId, String pName, String pDesc, Double budget, Set<Employee> employees, Department department) {
        this.pId = pId;
        this.pName = pName;
        this.pDesc = pDesc;
        this.budget = budget;
        this.employees = employees;
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

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}
