package com.employee.management.system.service;


import com.employee.management.system.entity.Department;
import com.employee.management.system.entity.Employee;
import com.employee.management.system.entity.Project;
import com.employee.management.system.repository.DepartmentRepository;
import com.employee.management.system.repository.EmployeeRepository;
import com.employee.management.system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AssignmentService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProjectRepository projectRepository;



    public Employee assignDepartmentToEmployee(Long deptId, Long  empId){
        Employee dbEmployee = employeeRepository.findById(empId).get();
        Department dbDepartment = departmentRepository.findById(deptId).get();
        List<Employee> employeeList = dbDepartment.getEmployees();
        employeeList.add(dbEmployee);
        dbDepartment.setEmployees(employeeList);
        dbEmployee.setDepartment(dbDepartment);
        departmentRepository.save(dbDepartment);
        return dbEmployee;

    }


    public Project assignProjectToEmployee(Long projId, Long empId) {
        Employee dbEmployee = employeeRepository.findById(empId).get();
        Project dbProject = projectRepository.findById(projId).get();
        Set<Project> projectList = dbEmployee.getProjects();
        Set<Employee> employeeList = dbProject.getEmployees();

        employeeList.add(employeeRepository.findById(empId).get());
        projectList.add(projectRepository.findById(projId).get());

        dbProject.setEmployees(employeeList);
        dbEmployee.setProjects(projectList);
        return projectRepository.save(dbProject);
    }


    public Department assignProjectToDepartment(Long deptId, Long projId) {
        Department savedDepartment = departmentRepository.findById(deptId).get();
        Project savedProject = projectRepository.findById(projId).get();

        List<Project> projects = savedDepartment.getProjects();
        projects.add(savedProject);
        savedDepartment.setProjects(projects);
        savedProject.setDepartment(savedDepartment);
        return departmentRepository.save(savedDepartment);
    }
}
