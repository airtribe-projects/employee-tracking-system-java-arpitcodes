package com.employee.management.system.controller;

import com.employee.management.system.entity.Project;
import com.employee.management.system.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        Project getDbProject = projectService.getProjectByName(project.getpName());
        if(null== getDbProject){
            Project savedProject = projectService.createProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(){
        List<Project> projectList = projectService.getProjects();
        return ResponseEntity.status(HttpStatus.OK).body(projectList);
    }

}
