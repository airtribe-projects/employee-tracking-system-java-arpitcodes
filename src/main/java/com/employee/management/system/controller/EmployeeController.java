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

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }


    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee(){
        List<Employee> employeesList = employeeService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employeesList);
    }


    @GetMapping("/{empId}")
    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable Long empId){
        Optional<Employee> emp = employeeService.findById(empId);
        if(emp.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(emp);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee employee){
        Optional<Employee> emp = employeeService.returnEmployee(employee);
        if(emp.isPresent()){
           Employee updatedEmployee =  employeeService.updateEmployee(employee);
           return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @DeleteMapping("/{empId}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long empId){
        Optional<Employee> emp = employeeService.findById(empId);
        if(emp.isPresent()){
            this.employeeService.delete(emp.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }

}
