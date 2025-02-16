package com.employee.management.system.controller;

import com.employee.management.system.entity.Department;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.BenchResources;
import com.employee.management.system.model.Budget;
import com.employee.management.system.model.DeleteResponse;
import com.employee.management.system.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private Department department;
    private Budget budget;
    private DeleteResponse deleteResponse;
    private BenchResources benchResources;

    @BeforeEach
    void setUp() {
        department = new Department();
        budget = new Budget();
        deleteResponse = new DeleteResponse();
        benchResources = new BenchResources();
    }

    @Test
    void getAllDepartments_Success() {
        when(departmentService.getAllDepartment()).thenReturn(Collections.singletonList(department));
        ResponseEntity<List<Department>> response = departmentController.getALlDepartment();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(departmentService, times(1)).getAllDepartment();
    }

    @Test
    void getDepartmentById_Success() {
        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.of(department));
        ResponseEntity<Optional<Department>> response = departmentController.getDepartmentById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
    }

    @Test
    void getDepartmentById_NotFound() {
        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Optional<Department>> response = departmentController.getDepartmentById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createDepartment_Success() {
        when(departmentService.getDepartmentByName(anyString())).thenReturn(null);
        when(departmentService.createDepartment(any(Department.class))).thenReturn(department);

        ResponseEntity<Department> response = departmentController.createDepartment(department);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(department, response.getBody());

        verify(departmentService, times(1)).getDepartmentByName(anyString());
        verify(departmentService, times(1)).createDepartment(any(Department.class));
    }


    @Test
    void createDepartment_Conflict() {
        when(departmentService.getDepartmentByName(anyString())).thenReturn(department);
        ResponseEntity<Department> response = departmentController.createDepartment(department);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void budgetOfProjectsInADepartment_Success() {
        when(departmentService.getTotalBudget(anyString())).thenReturn(budget);
        ResponseEntity<Budget> response = departmentController.budgetOfProjectsInADepartment("IT");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateDepartment_Success() {
        when(departmentService.updateDepartment(any(Department.class))).thenReturn(department);
        ResponseEntity<Department> response = departmentController.updateDepartment(department);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteDepartment_Success() {
        when(departmentService.deleteDepartmentById(1L)).thenReturn(deleteResponse);
        ResponseEntity<DeleteResponse> response = departmentController.deleteDepartment(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getBenchResources_Success() {
        when(departmentService.getAllBenchResources()).thenReturn(Collections.singletonList(benchResources));
        ResponseEntity<List<BenchResources>> response = departmentController.getBenchResources();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
