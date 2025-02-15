package com.employee.management.system.service;

import com.employee.management.system.entity.Project;
import com.employee.management.system.exception.NullPointerException;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.DeleteResponse;
import com.employee.management.system.repository.EmployeeRepository;
import com.employee.management.system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Project createProject(Project project) {

        return projectRepository.save(project);
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectByName(String s) {
        if (projectRepository.findAll() != null) {
            List<Project> projects = projectRepository.findAll();
            for (Project project : projects) {
                if (project.getpName().equals(s)) {
                    return project;
                }
            }
        }

        return null;
    }

    public Project updateProject(Project project) {
        try {
            Project savedProject = projectRepository.findById(project.getpId()).get();
            if (savedProject != null) {
                savedProject.setpName(project.getpName());
                savedProject.setDepartment(project.getDepartment());
                savedProject.setpDesc(project.getpDesc());
                savedProject.setBudget(project.getBudget());
                return projectRepository.save(savedProject);

            }
            throw new NullPointerException("Null point exception occured!");

        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }


    public DeleteResponse deleteProject(Long projId) {
        try{
            projectRepository.delete(projectRepository.findById(projId).get());
            DeleteResponse deleteResponseBody = new DeleteResponse();
            deleteResponseBody.setMessage("Project Deleted Successfully!");
            deleteResponseBody.setStatusCode(HttpStatus.NO_CONTENT.value());
            return deleteResponseBody;
        }catch (Exception ex){
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }
}
