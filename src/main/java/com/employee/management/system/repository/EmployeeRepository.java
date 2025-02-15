package com.employee.management.system.repository;

import com.employee.management.system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.firstName = :name")
    List<Employee> findEmployeeByName(@Param("name") String name);

    @Query("SELECT e FROM Employee e WHERE e.department.deptName = :department")
    List<Employee> findEmployeeByDepartmnt(@Param("department") String department);

    @Query("SELECT e FROM Employee e JOIN e.projects p WHERE p.pName = :pName")
    List<Employee> findEmployeeByProjectName(@Param("pName") String pName);

}
