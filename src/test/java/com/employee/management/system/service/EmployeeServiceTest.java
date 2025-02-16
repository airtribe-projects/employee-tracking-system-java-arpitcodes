package com.employee.management.system.service;

import com.employee.management.system.entity.Employee;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.DeleteResponse;
import com.employee.management.system.repository.DepartmentRepository;
import com.employee.management.system.repository.EmployeeRepository;
import com.employee.management.system.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEmpId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
    }

    @Test
    void testReturnEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Optional<Employee> foundEmployee = employeeService.returnEmployee(1L);
        assertTrue(foundEmployee.isPresent());
        assertEquals("John", foundEmployee.get().getFirstName());
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee savedEmployee = employeeService.createEmployee(employee);
        assertNotNull(savedEmployee);
        assertEquals("John", savedEmployee.getFirstName());
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
        List<Employee> employees = employeeService.getAllEmployees();
        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
    }

    @Test
    void testDeleteEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).deleteById(1L);
        DeleteResponse response = employeeService.deletEmployeeById(1L);
        assertEquals(204, response.getStatusCode());
    }

    @Test
    void testDeleteEmployeeByIdNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeService.deletEmployeeById(1L));
    }

    @Test
    void testUpdateEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        employee.setFirstName("Jane");
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        assertEquals("Jane", updatedEmployee.getFirstName());
    }

    @Test
    void testIsEmployeeRegistered() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
        Employee foundEmployee = employeeService.isEmployeeRegistered("john.doe@example.com");
        assertNotNull(foundEmployee);
        assertEquals("John", foundEmployee.getFirstName());
    }
}