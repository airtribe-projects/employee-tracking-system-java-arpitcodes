package com.employee.management.system.controller;


import com.employee.management.system.entity.Employee;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.*;
import com.employee.management.system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    //create employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee getDbEmployee = employeeService.isEmployeeRegistered(employee.getEmail());
            if (getDbEmployee == null) {
                Employee savedEmployee = employeeService.createEmployee(employee);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }


    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee() {
        try {
            List<Employee> employeesList = employeeService.getAllEmployees();
            return ResponseEntity.status(HttpStatus.OK).body(employeesList);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }


    }


    @GetMapping("/{empId}")
    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable Long empId) {
        try {
            Optional<Employee> emp = employeeService.findById(empId);
            if (emp.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(emp);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }


    }

    @GetMapping("/search/names")
    public ResponseEntity<List<EmployeeSearch>> searchEmployeeByName(@RequestParam("name") String empName) {
        try {
            return ResponseEntity.ok(employeeService.searchEmployeeUsingName(empName));

        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }


    @GetMapping("/search/departments")
    public ResponseEntity<DepartmentSearch> searchEmployeeUsingDepartment(@RequestParam("department") String department) {
        try {
            return ResponseEntity.ok(employeeService.searchEmployeeUsingDepartment(department));

        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }

    @GetMapping("/search/projects")
    public ResponseEntity<ProjectSearch> searchEmployeeUsingProject(@RequestParam("project") String project) {
        try {
            return ResponseEntity.ok(employeeService.searchEmployeeUsingProject(project));

        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }

    @GetMapping("/projects")
    public ResponseEntity<ProjectSearch> searchEmployeeUsingProjectId(@RequestParam("projId") String projId) {
        try {
            return ResponseEntity.ok(employeeService.searchEmployeesOnProject(projId));
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }


    @PatchMapping
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.updateEmployee(employee));
        }catch (Exception ex){
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }


    @DeleteMapping("/{empId}")
    public ResponseEntity<DeleteResponse> deleteEmployee(@PathVariable Long empId) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(employeeService.deletEmployeeById(empId));
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage()), HttpStatus.NOT_FOUND);
    }


}
