package com.employee.management.system.controller;


import com.employee.management.system.entity.Department;
import com.employee.management.system.service.DepartmentService;
import org.apache.coyote.Response;
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
    public ResponseEntity<List<Department>> getALlDepartment(){
        List<Department> departmentList = departmentService.getAllDepartment();
        return ResponseEntity.status(HttpStatus.OK).body(departmentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Department>> getDepartmentById(@PathVariable Long id){
        Optional<Department> department = departmentService.getDepartmentById(id);
        if(department.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(department);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping
    public ResponseEntity<Department> createDepartments(@RequestBody Department departmentRequestBody){
//        Optional<Department> department = departmentService.getDepartmentById(departmentRequestBody.getDeptId());
//        if(department.isPresent()){
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
        Department savedDepartment = departmentService.createDepartment(departmentRequestBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }

}
