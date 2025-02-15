package com.employee.management.system.service;

import com.employee.management.system.entity.Department;
import com.employee.management.system.entity.Employee;
import com.employee.management.system.entity.Project;
import com.employee.management.system.exception.NullPointerException;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.BenchResources;
import com.employee.management.system.model.Budget;
import com.employee.management.system.model.DeleteResponse;
import com.employee.management.system.repository.DepartmentRepository;
import com.employee.management.system.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Department createDepartment(Department departmentRequestBody) {
        return departmentRepository.save(departmentRequestBody);
    }

    public Department getDepartmentByName(String deptName) {
        List<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            if (department.getDeptName().equals(deptName)) {
                System.out.println("Departmnet already exist!");
                return department;
            }
        }
        return null;
    }

    public Budget getTotalBudget(String deptName) {
        Department department = departmentRepository.findDepartment(deptName);
        List<Project> projectsInDepartment = department.getProjects();
        Budget budgetResponse = new Budget();
        int countOfProject = 0;
        Long totalBudget = 0L;
        for (Project project : projectsInDepartment) {
            totalBudget += project.getBudget().longValue();
            countOfProject++;
        }

        budgetResponse.setDeptId(department.getDeptId());
        budgetResponse.setDeptName(department.getDeptName());
        budgetResponse.setBudget(Long.toString(totalBudget));
        budgetResponse.setNoOfProjects(countOfProject);
        return budgetResponse;
    }

    public List<BenchResources> getAllBenchResources() {
        List<Employee> allEmployees = employeeRepository.findAll();

        List<BenchResources> benchResourcesList = new ArrayList<>();
        for (Employee emp : allEmployees) {
            if (emp.getProjects().size() == 0) {
                BenchResources benchResources = new BenchResources();
                benchResources.setEmpId(null != emp.getEmpId().toString() ? emp.getEmpId().toString() : null);
                benchResources.setEmpName((null != emp.getFirstName() ? emp.getFirstName() : null) + " " + (null != emp.getLastName() ? emp.getLastName() : null));
                benchResources.setDeptName(null != emp.getDepartment() && null != emp.getDepartment().getDeptName() ? emp.getDepartment().getDeptName() : null);
                benchResourcesList.add(benchResources);
            }
        }
        return benchResourcesList;
    }

    public Department updateDepartment(Department department) {
        try {
            Department savedDepartment = departmentRepository.findById(department.getDeptId()).get();
            if (savedDepartment != null) {

                savedDepartment.setDeptName(department.getDeptName());
                savedDepartment.setDeptDescription(department.getDeptDescription());
                departmentRepository.save(savedDepartment);
                return savedDepartment;
            }
            throw new NullPointerException("No department found with name: " + department.getDeptName());


        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }


    }

    public DeleteResponse deleteDepartmentById(Long deptId) {
        try {
            DeleteResponse deleteResponse = new DeleteResponse(HttpStatus.NO_CONTENT.value(), "Department " + departmentRepository.findById(deptId).get().getDeptName() + " Deleted successfully!");
            departmentRepository.delete(departmentRepository.findById(deptId).get());
            return deleteResponse;
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }
}
