package com.employee.management.system.controller;


import com.employee.management.system.entity.Employee;
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
        Employee getDbEmployee = employeeService.isEmployeeRegistered(employee.getEmail());
        if(getDbEmployee == null){
            Employee savedEmployee = employeeService.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }



    //get employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee(){
        List<Employee> employeesList = employeeService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employeesList);
    }


    //get employee by id
    @GetMapping("/{empId}")
    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable Long empId){
        Optional<Employee> emp = employeeService.findById(empId);
        if(emp.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(emp);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
