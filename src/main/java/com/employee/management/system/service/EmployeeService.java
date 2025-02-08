package com.employee.management.system.service;

import com.employee.management.system.entity.Employee;
import com.employee.management.system.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> returnEmployee(Employee employee) {
        return employeeRepository.findById(employee.getEmpId());
    }


    public Optional<Employee> returnEmployee(Long empId){
        return employeeRepository.findById(empId);
    }

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void deletEmployeeById(Long empId) {
         employeeRepository.deleteById(empId);
    }

    public Optional<Employee> findById(Long empId) {
        return employeeRepository.findById(empId);
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
