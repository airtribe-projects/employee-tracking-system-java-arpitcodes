package com.employee.management.system.service;

import com.employee.management.system.entity.Department;
import com.employee.management.system.entity.Employee;
import com.employee.management.system.entity.Project;
import com.employee.management.system.repository.DepartmentRepository;
import com.employee.management.system.repository.EmployeeRepository;
import com.employee.management.system.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssignmentServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    private Employee employee;
    private Department department;
    private Project project;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEmpId(1L);
        employee.setProjects(new HashSet<>());

        department = new Department();
        department.setDeptId(1L);
        department.setEmployees(new ArrayList<>());
        department.setProjects(new ArrayList<>());

        project = new Project();
        project.setpId(1L);
        project.setEmployees(new HashSet<>());
    }

    @Test
    void testAssignDepartmentToEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Employee result = assignmentService.assignDepartmentToEmployee(1L, 1L);

        assertNotNull(result);
        assertEquals(department, result.getDepartment());
        assertTrue(department.getEmployees().contains(employee));
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void testAssignProjectToEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project result = assignmentService.assignProjectToEmployee(1L, 1L);

        assertNotNull(result);
        assertTrue(result.getEmployees().contains(employee));
        assertTrue(employee.getProjects().contains(project));
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testAssignProjectToDepartment() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department result = assignmentService.assignProjectToDepartment(1L, 1L);

        assertNotNull(result);
        assertTrue(result.getProjects().contains(project));
        assertEquals(department, project.getDepartment());
        verify(departmentRepository, times(1)).save(department);
    }
}