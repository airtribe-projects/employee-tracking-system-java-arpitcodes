package com.employee.management.system.service;

import com.employee.management.system.entity.Project;
import com.employee.management.system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(Project project) {

        return projectRepository.save(project);
    }

    public List<Project> getProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectByName(String s) {
        if(projectRepository.findAll()!=null){
        List<Project> projects = projectRepository.findAll();
        for(Project project : projects){
            if(project.getpName().equals(s)){
                return project;
            }
        }
        }

        return null;
    }
}
