package com.employee.management.system.services;

import com.employee.management.system.entity.Department;
import com.employee.management.system.entity.Employee;
import com.employee.management.system.entity.Project;
import com.employee.management.system.repository.DepartmentRepository;
import com.employee.management.system.repository.EmployeeRepository;
import com.employee.management.system.repository.ProjectRepository;
import com.employee.management.system.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private EmployeeService employeeService;

    // Utility data for tests
    private Employee mockEmployee;
    private Department mockDepartment;
    private Project mockProject;

    @BeforeEach
    void setUp() {
        mockEmployee = new Employee(1L, "John", "Doe", "john.doe@example.com", "1234567890", null, null);
    }
}
