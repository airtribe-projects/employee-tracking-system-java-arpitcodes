package com.employee.management.system.service;

import com.employee.management.system.entity.Department;
import com.employee.management.system.entity.Employee;
import com.employee.management.system.entity.Project;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.DeleteResponse;
import com.employee.management.system.model.DepartmentSearch;
import com.employee.management.system.model.EmployeeSearch;
import com.employee.management.system.model.ProjectSearch;
import com.employee.management.system.repository.DepartmentRepository;
import com.employee.management.system.repository.EmployeeRepository;
import com.employee.management.system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Optional<Employee> returnEmployee(Employee employee) {
        return employeeRepository.findById(employee.getEmpId());
    }


    public Optional<Employee> returnEmployee(Long empId){
        return employeeRepository.findById(empId);
    }

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }


    @Cacheable
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public DeleteResponse deletEmployeeById(Long empId) {
        try{
            DeleteResponse deleteResponse = new DeleteResponse(HttpStatus.NO_CONTENT.value(), "Employee "+employeeRepository.findById(empId).get().getEmpId()+" has been deleted!");
            employeeRepository.deleteById(empId);
            return deleteResponse;
        }catch(Exception ex){
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    public Optional<Employee> findById(Long empId) {
        return employeeRepository.findById(empId);
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
    public Employee updateEmployee(Employee employee) {
        try{
            Employee savedEmployee = employeeRepository.findById(employee.getEmpId()).get();
            savedEmployee.setFirstName(employee.getFirstName());
            savedEmployee.setLastName(employee.getLastName());
            savedEmployee.setEmail(employee.getEmail());
            savedEmployee.setPhoneNumber(employee.getPhoneNumber());
            employeeRepository.save(savedEmployee);
            return savedEmployee;
        }catch (Exception ex){
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    public Employee isEmployeeRegistered(String email) {
        List<Employee> employeeList = employeeRepository.findAll();
        for(Employee employee : employeeList){
            if(employee.getEmail().equals(email)){
                return employee;
            }
        }
        return null;
    }

    public List<EmployeeSearch> searchEmployeeUsingName(String searchKeyWord) {
                List<Employee> employees = employeeRepository.findEmployeeByName(searchKeyWord);
                List<EmployeeSearch> listToBeReturned = new ArrayList<>();
                if(null!=employees && employees.size() !=0){
                    for(Employee emp : employees){
                        EmployeeSearch employeeSearch = new EmployeeSearch();
                        employeeSearch.setEmpId(emp.getEmpId());
                        employeeSearch.setFirstName(emp.getFirstName());
                        employeeSearch.setLastName(emp.getLastName());
                        employeeSearch.setPhoneNumber(emp.getPhoneNumber());
                        employeeSearch.setEmail(emp.getEmail());
                        listToBeReturned.add(employeeSearch);
                    }
                }
                return listToBeReturned;
    }

    public DepartmentSearch searchEmployeeUsingDepartment( String department) {
        List<Employee> employees = employeeRepository.findEmployeeByDepartmnt(department);
        List<EmployeeSearch> presentEmployeeSearches = new ArrayList<>();
        Department dept = departmentRepository.findDepartment(department);
        DepartmentSearch departmentDetails = new DepartmentSearch();
        departmentDetails.setDeptId(dept.getDeptId());
        departmentDetails.setDeptName(dept.getDeptName());
        departmentDetails.setDeptDesc(dept.getDeptDescription());
        if(null!=employees && employees.size() !=0){
            for(Employee emp : employees){
                presentEmployeeSearches = addEmployees(presentEmployeeSearches, emp);
            }}
        departmentDetails.setEmployees(presentEmployeeSearches);
        return departmentDetails;
    }
    private List<EmployeeSearch> addEmployees(List<EmployeeSearch> existingList, Employee employee){
        EmployeeSearch employeeSearchToBeAdded = new EmployeeSearch(employee.getEmpId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPhoneNumber(),
                employee.getEmail());

        existingList.add(employeeSearchToBeAdded);
        return existingList;
    }


    public ProjectSearch searchEmployeeUsingProject(String project) {

        List<Employee> employees = employeeRepository.findEmployeeByProjectName(project);
        Project savedProject = projectRepository.findByName(project);
        ProjectSearch projectToBeReturned = new ProjectSearch();
        projectToBeReturned.setpId(savedProject.getpId());
        projectToBeReturned.setpName(savedProject.getpName());
        projectToBeReturned.setpDesc(savedProject.getpDesc());
        ArrayList<EmployeeSearch> employeeList = new ArrayList<>();
        for(Employee emp : employees){
            EmployeeSearch employeeSearch = new EmployeeSearch();
            employeeSearch.setEmpId(emp.getEmpId());
            employeeSearch.setFirstName(emp.getFirstName());
            employeeSearch.setLastName(emp.getLastName());
            employeeSearch.setEmail(emp.getEmail());
            employeeSearch.getPhoneNumber(emp.getPhoneNumber());
            employeeList.add(employeeSearch);
        }
        projectToBeReturned.setEmployeeList(employeeList);
        return  projectToBeReturned;
    }

    public ProjectSearch searchEmployeesOnProject(String projId) {
        Project project = projectRepository.findById(Long.valueOf(projId)).get();
        ProjectSearch proj = new ProjectSearch();
        proj.setpId(project.getpId());
        proj.setpName(project.getpName());
        proj.setpDesc(project.getpDesc());

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeSearch> employeeSearchList = new ArrayList<>();

        for(Employee emp : employees){
            if(null!=emp.getProjects()){
                for(Project assignedProjects : emp.getProjects()){
                    if(assignedProjects.getpId()==project.getpId()){
                            EmployeeSearch employee = new EmployeeSearch();
                            employee.setEmpId(emp.getEmpId());
                            employee.setFirstName(emp.getFirstName());
                            employee.setLastName(emp.getLastName());
                            employee.setPhoneNumber(emp.getPhoneNumber());
                            employee.setEmail(emp.getEmail());
                            employeeSearchList.add(employee);
                    }
                }
            }
        }
        proj.setEmployeeList(employeeSearchList);
        return proj;
    }
}
