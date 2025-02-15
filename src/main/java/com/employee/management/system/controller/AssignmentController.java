package com.employee.management.system.controller;


import com.employee.management.system.entity.Department;
import com.employee.management.system.entity.Employee;
import com.employee.management.system.entity.Project;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assign")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;


    //assign project to employee
    @PostMapping("/project")
    public Project assignProject(@RequestParam("projId") Long projId, @RequestParam("empId") Long empId) {
        try{
           return assignmentService.assignProjectToEmployee(projId, empId);
        }catch(Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }


    @PostMapping("/department")
    public Employee assignEmployee(@RequestParam("deptId") Long deptId, @RequestParam("empId") Long empId) {
        try{
            return assignmentService.assignDepartmentToEmployee(deptId, empId);
        }catch(Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @PostMapping("/project-to-department")
    public Department assignProjectToDepartment(@RequestParam("deptId") Long deptId, @RequestParam("projId") Long projId) {
        try{
            return assignmentService.assignProjectToDepartment(deptId, projId);
        }catch(Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}

