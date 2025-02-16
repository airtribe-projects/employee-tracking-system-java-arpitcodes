package com.employee.management.system.controller;

import com.employee.management.system.entity.Department;
import com.employee.management.system.entity.Employee;
import com.employee.management.system.entity.Project;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.service.AssignmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssignmentControllerTest {

    @Mock
    private AssignmentService assignmentService;

    @InjectMocks
    private AssignmentController assignmentController;

    private Employee employee;
    private Project project;
    private Department department;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        project = new Project();
        department = new Department();
    }

    @Test
    void assignProject_Success() {
        when(assignmentService.assignProjectToEmployee(1L, 1L)).thenReturn(project);
        Project result = assignmentController.assignProject(1L, 1L);
        assertNotNull(result);
        verify(assignmentService, times(1)).assignProjectToEmployee(1L, 1L);
    }

    @Test
    void assignProject_ThrowsException() {
        when(assignmentService.assignProjectToEmployee(1L, 1L)).thenThrow(new RuntimeException("Project not found"));
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                assignmentController.assignProject(1L, 1L));
        assertEquals("Project not found", thrown.getMessage());
    }

    @Test
    void assignEmployee_Success() {
        when(assignmentService.assignDepartmentToEmployee(1L, 1L)).thenReturn(employee);
        Employee result = assignmentController.assignEmployee(1L, 1L);
        assertNotNull(result);
        verify(assignmentService, times(1)).assignDepartmentToEmployee(1L, 1L);
    }

    @Test
    void assignEmployee_ThrowsException() {
        when(assignmentService.assignDepartmentToEmployee(1L, 1L)).thenThrow(new RuntimeException("Employee not found"));
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                assignmentController.assignEmployee(1L, 1L));
        assertEquals("Employee not found", thrown.getMessage());
    }

    @Test
    void assignProjectToDepartment_Success() {
        when(assignmentService.assignProjectToDepartment(1L, 1L)).thenReturn(department);
        Department result = assignmentController.assignProjectToDepartment(1L, 1L);
        assertNotNull(result);
        verify(assignmentService, times(1)).assignProjectToDepartment(1L, 1L);
    }

    @Test
    void assignProjectToDepartment_ThrowsException() {
        when(assignmentService.assignProjectToDepartment(1L, 1L)).thenThrow(new RuntimeException("Department not found"));
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                assignmentController.assignProjectToDepartment(1L, 1L));
        assertEquals("Department not found", thrown.getMessage());
    }

    @Test
    void handleResourceNotFound() {
        ResponseEntity<String> response = assignmentController.handleResourceNotFound(new ResourceNotFoundException("Resource not found"));
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Resource not found", response.getBody());
    }
}