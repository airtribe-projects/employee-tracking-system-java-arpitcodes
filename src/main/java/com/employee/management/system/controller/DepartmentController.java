package com.employee.management.system.controller;


import com.employee.management.system.entity.Department;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.BenchResources;
import com.employee.management.system.model.Budget;
import com.employee.management.system.model.DeleteResponse;
import com.employee.management.system.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getALlDepartment() {
        try{
            List<Department> departmentList = departmentService.getAllDepartment();
            return ResponseEntity.status(HttpStatus.OK).body(departmentList);
        }catch (RuntimeException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Department>> getDepartmentById(@PathVariable Long id) {
        try{
            Optional<Department> department = departmentService.getDepartmentById(id);
            if (department.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(department);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }

    }


    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        try{
            Department dbDepartment = departmentService.getDepartmentByName(department.getDeptName());
            if (dbDepartment == null) {
                Department savedDepartment = departmentService.createDepartment(department);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }catch(Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }

    }


    @GetMapping("/budget")
    public ResponseEntity<Budget> budgetOfProjectsInADepartment(@RequestParam("deptName") String deptName) {
        try{
            return ResponseEntity.ok(departmentService.getTotalBudget(deptName));

        }catch(RuntimeException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(departmentService.updateDepartment(department));
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @DeleteMapping("/{deptId}")
    public ResponseEntity<DeleteResponse> deleteDepartment(@PathVariable Long deptId){
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(departmentService.deleteDepartmentById(deptId));
        }catch (Exception ex){
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @GetMapping("/bench-resources")
    public ResponseEntity<List<BenchResources>> getBenchResources() {
        return ResponseEntity.ok(departmentService.getAllBenchResources());
    }

}
