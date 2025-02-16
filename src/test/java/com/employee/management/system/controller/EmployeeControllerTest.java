
package com.employee.management.system.controller;

import com.employee.management.system.entity.Employee;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.*;
import com.employee.management.system.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee;
    private List<Employee> employeeList;

    @BeforeEach public void setup() {
        employee = new Employee();
        employee.setEmail("test@example.com");
        employeeList = new ArrayList<>();
        employeeList.add(employee);
    }

    @Test
    public void testCreateEmployee_WhenEmployeeNotRegistered() {
        when(employeeService.isEmployeeRegistered(employee.getEmail())).thenReturn(null);
        when(employeeService.createEmployee(employee)).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.createEmployee(employee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test public void testCreateEmployee_WhenEmployeeAlreadyRegistered() {
        when(employeeService.isEmployeeRegistered(employee.getEmail())).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.createEmployee(employee);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateEmployee_Exception() {
        when(employeeService.isEmployeeRegistered(employee.getEmail())).thenThrow(new RuntimeException("Exception"));

        try {
            employeeController.createEmployee(employee);
        } catch (ResourceNotFoundException ex) {
            assertEquals("Exception", ex.getMessage());
        }
    }

    @Test public void testGetAllEmployees() {
        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployee();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeList, response.getBody());
    }

    @Test public void testGetAllEmployees_Exception() {
        when(employeeService.getAllEmployees()).thenThrow(new RuntimeException("Exception"));

        try {
            employeeController.getAllEmployee();
        } catch (ResourceNotFoundException ex) {
            assertEquals("Exception", ex.getMessage());
        }
    }

    @Test
    public void testGetEmployeeById_WhenEmployeeExists() {
        when(employeeService.findById(1L)).thenReturn(Optional.of(employee));

        ResponseEntity<Optional<Employee>> response = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(employee), response.getBody());
    }

    @Test
    public void testGetEmployeeById_WhenEmployeeNotExists() {
        when(employeeService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Optional<Employee>> response = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetEmployeeById_Exception() {
        when(employeeService.findById(1L)).thenThrow(new RuntimeException("Exception"));

        try {
            employeeController.getEmployeeById(1L);
        } catch (ResourceNotFoundException ex) {
            assertEquals("Exception", ex.getMessage());
        }
    }

    @Test public void testSearchEmployeeByName() {
        List<EmployeeSearch> employeeSearchList = new ArrayList<>();
        when(employeeService.searchEmployeeUsingName("John")).thenReturn(employeeSearchList);

        ResponseEntity<List<EmployeeSearch>> response = employeeController.searchEmployeeByName("John");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeSearchList, response.getBody());
    }

    @Test
    public void testSearchEmployeeByName_Exception() {
        when(employeeService.searchEmployeeUsingName("John")).thenThrow(new RuntimeException("Exception"));

        try {
            employeeController.searchEmployeeByName("John");
        } catch (ResourceNotFoundException ex) {
            assertEquals("Exception", ex.getMessage());
        }
    }

    @Test
    public void testSearchEmployeeByDepartment() {
        DepartmentSearch departmentSearch = new DepartmentSearch();
        when(employeeService.searchEmployeeUsingDepartment("HR")).thenReturn(departmentSearch);

        ResponseEntity<DepartmentSearch> response = employeeController.searchEmployeeUsingDepartment("HR");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departmentSearch, response.getBody());
    }

    @Test
    public void testSearchEmployeeByDepartment_Exception() {
        when(employeeService.searchEmployeeUsingDepartment("HR")).thenThrow(new RuntimeException("Exception"));

        try {
            employeeController.searchEmployeeUsingDepartment("HR");
        } catch (ResourceNotFoundException ex) {
            assertEquals("Exception", ex.getMessage());
        }
    }

    @Test
    public void testSearchEmployeeByProject() {
        ProjectSearch projectSearch = new ProjectSearch();
        when(employeeService.searchEmployeeUsingProject("ProjectA")).thenReturn(projectSearch);

        ResponseEntity<ProjectSearch> response = employeeController.searchEmployeeUsingProject("ProjectA");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectSearch, response.getBody());
    }

    @Test public void testSearchEmployeeByProject_Exception() {
        when(employeeService.searchEmployeeUsingProject("ProjectA")).thenThrow(new RuntimeException("Exception"));

        try {
            employeeController.searchEmployeeUsingProject("ProjectA");
        } catch (ResourceNotFoundException ex) {
            assertEquals("Exception", ex.getMessage());
        }
    }

    @Test public void testSearchEmployeeByProjectId() {
        ProjectSearch projectSearch = new ProjectSearch();
        when(employeeService.searchEmployeesOnProject("123")).thenReturn(projectSearch);

        ResponseEntity<ProjectSearch> response = employeeController.searchEmployeeUsingProjectId("123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectSearch, response.getBody());
    }

    @Test
    public void testSearchEmployeeByProjectId_Exception() {
        when(employeeService.searchEmployeesOnProject("123")).thenThrow(new RuntimeException("Exception"));

        try {
            employeeController.searchEmployeeUsingProjectId("123");
        } catch (ResourceNotFoundException ex) {
            assertEquals("Exception", ex.getMessage());
        }
    }

    @Test
    public void testUpdateEmployee() {
        when(employeeService.updateEmployee(employee)).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.updateEmployee(employee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    public void testUpdateEmployee_Exception() {
        when(employeeService.updateEmployee(employee)).thenThrow(new RuntimeException("Exception"));

        try {
            employeeController.updateEmployee(employee);
        } catch (ResourceNotFoundException ex) {
            assertEquals("Exception", ex.getMessage());
        }
    }

    @Test
    public void testDeleteEmployee() {
        DeleteResponse deleteResponse = new DeleteResponse();
        when(employeeService.deletEmployeeById(1L)).thenReturn(deleteResponse);

        ResponseEntity<DeleteResponse> response = employeeController.deleteEmployee(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(deleteResponse, response.getBody());
    }

    @Test
    public void testDeleteEmployee_Exception() {
        when(employeeService.deletEmployeeById(1L)).thenThrow(new RuntimeException("Exception"));

        try {
            employeeController.deleteEmployee(1L);
        } catch (ResourceNotFoundException ex) {
            assertEquals("Exception", ex.getMessage());
        }
    }
}
