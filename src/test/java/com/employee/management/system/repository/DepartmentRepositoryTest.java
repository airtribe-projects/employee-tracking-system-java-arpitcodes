package com.employee.management.system.repository;

import com.employee.management.system.entity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        Department department = new Department();
        department.setDeptName("HR");
        departmentRepository.save(department);
    }

    @Test
    void testFindDepartment() {
        Department foundDepartment = departmentRepository.findDepartment("HR");
        assertThat(foundDepartment).isNotNull();
        assertThat(foundDepartment.getDeptName()).isEqualTo("HR");
    }

    @Test
    void testFindDepartment_NotFound() {
        Department foundDepartment = departmentRepository.findDepartment("Finance");
        assertThat(foundDepartment).isNull();
    }
}
