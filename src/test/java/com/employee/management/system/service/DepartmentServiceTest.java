package com.employee.management.system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.employee.management.system.entity.Department;
import com.employee.management.system.entity.Employee;
import com.employee.management.system.entity.Project;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.BenchResources;
import com.employee.management.system.model.Budget;
import com.employee.management.system.model.DeleteResponse;
import com.employee.management.system.repository.DepartmentRepository;
import com.employee.management.system.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setDeptId(1L);
        department.setDeptName("IT");
        department.setDeptDescription("IT Department");
    }

    @Test
    void testGetAllDepartment() {
        List<Department> departments = Arrays.asList(department);
        when(departmentRepository.findAll()).thenReturn(departments);

        List<Department> result = departmentService.getAllDepartment();
        assertEquals(1, result.size());
        assertEquals("IT", result.get(0).getDeptName());
    }

    @Test
    void testGetDepartmentById() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Optional<Department> result = departmentService.getDepartmentById(1L);
        assertTrue(result.isPresent());
        assertEquals("IT", result.get().getDeptName());
    }

    @Test
    void testCreateDepartment() {
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = departmentService.createDepartment(department);
        assertNotNull(result);
        assertEquals("IT", result.getDeptName());
    }

    @Test
    void testGetDepartmentByName() {
        when(departmentRepository.findAll()).thenReturn(Collections.singletonList(department));

        Department result = departmentService.getDepartmentByName("IT");
        assertNotNull(result);
        assertEquals("IT", result.getDeptName());
    }

    @Test
    void testGetTotalBudget() {
        Project project = new Project();
        project.setBudget(10000.0);
        List<Project> projects = Collections.singletonList(project);
        department.setProjects(projects);

        when(departmentRepository.findDepartment("IT")).thenReturn(department);

        Budget result = departmentService.getTotalBudget("IT");
        assertNotNull(result);
        assertEquals("10000", result.getBudget());
        assertEquals(1, result.getNoOfProjects());
    }

    @Test
    void testGetAllBenchResources() {
        Employee emp = new Employee();
        emp.setEmpId(1L);
        emp.setFirstName("John");
        emp.setLastName("Doe");
        emp.setProjects(Collections.emptySet());
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(emp));

        List<BenchResources> result = departmentService.getAllBenchResources();
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getEmpName());
    }

    @Test
    void testUpdateDepartment() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = departmentService.updateDepartment(department);
        assertNotNull(result);
        assertEquals("IT", result.getDeptName());
    }

    @Test
    void testDeleteDepartmentById() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).delete(department);

        DeleteResponse result = departmentService.deleteDepartmentById(1L);
        assertEquals(204, result.getStatusCode());
    }

    @Test
    void testDeleteDepartmentById_ThrowsException() {
        when(departmentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> departmentService.deleteDepartmentById(2L));
    }
}
