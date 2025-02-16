package com.employee.management.system.repository;

import com.employee.management.system.entity.Department;
import com.employee.management.system.entity.Employee;
import com.employee.management.system.entity.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee1, employee2;

    @BeforeEach
    void setUp() {
        Department department = new Department();
        department.setDeptName("IT");

        Project project = new Project();
        project.setpName("ProjectX");

        employee1 = new Employee();
        employee1.setFirstName("John");
        employee1.setDepartment(department);
        employee1.setProjects(new HashSet<>(Arrays.asList(project)));

        employee2 = new Employee();
        employee2.setFirstName("Doe");
        employee2.setDepartment(department);
        employee2.setProjects(new HashSet<>(Arrays.asList(project)));

        employeeRepository.saveAll(Arrays.asList(employee1, employee2));
    }

    @Test
    void testFindEmployeeByName() {
        List<Employee> employees = employeeRepository.findEmployeeByName("John");
        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    void testFindEmployeeByDepartment() {
        List<Employee> employees = employeeRepository.findEmployeeByDepartmnt("IT");
        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getDepartment().getDeptName()).isEqualTo("IT");
    }

    @Test
    void testFindEmployeeByProjectName() {
        List<Employee> employees = employeeRepository.findEmployeeByProjectName("ProjectX");
        assertThat(employees).isNotEmpty();
        List<Employee> employeeList = employees;
        Employee employee = employees.get(0);
        ArrayList<Project> projects = new ArrayList<>(employee.getProjects());



        assertThat(projects.get(0).getpName()).isEqualTo("ProjectX");
    }
}
