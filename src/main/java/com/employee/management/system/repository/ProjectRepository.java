package com.employee.management.system.repository;

import com.employee.management.system.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository  extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project  p where p.pName = :pName")
    Project findByName(@Param("pName") String pName);
}
