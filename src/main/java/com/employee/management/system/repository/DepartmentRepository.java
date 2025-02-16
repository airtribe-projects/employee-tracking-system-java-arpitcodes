package com.employee.management.system.repository;

import com.employee.management.system.entity.Department;
import com.employee.management.system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d WHERE d.deptName = :deptName")
    Department findDepartment(String deptName);

}
