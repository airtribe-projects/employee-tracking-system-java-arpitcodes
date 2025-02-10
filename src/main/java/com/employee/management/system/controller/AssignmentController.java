package com.employee.management.system.controller;


import com.employee.management.system.entity.Department;
import com.employee.management.system.entity.Employee;
import com.employee.management.system.entity.Project;
import com.employee.management.system.service.AssignmentService;
import com.employee.management.system.service.DepartmentService;
import com.employee.management.system.service.EmployeeService;
import com.employee.management.system.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assign")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;


    //assign project to employee
    @PostMapping("/project")
    public Project assignProject(@RequestParam("projId") Long projId, @RequestParam("empId") Long empId){
        return assignmentService.assignProjectToEmployee(projId, empId);
    }


    @PostMapping("/department")
    public Department assignEmployee(@RequestParam("deptId") Long deptId, @RequestParam("empId") Long empId){
        return assignmentService.assignDepartmentToEmployee(deptId, empId);
    }

}

