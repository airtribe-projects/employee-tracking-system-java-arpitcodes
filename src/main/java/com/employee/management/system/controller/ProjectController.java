package com.employee.management.system.controller;

import com.employee.management.system.entity.Project;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.DeleteResponse;
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
        try{
            Project getDbProject = projectService.getProjectByName(project.getpName());
            if(null== getDbProject){
                Project savedProject = projectService.createProject(project);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }


    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(){
        try{
            List<Project> projectList = projectService.getProjects();
            return ResponseEntity.status(HttpStatus.OK).body(projectList);
        }catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Project> updateProject(@RequestBody Project project)
    {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(projectService.updateProject(project));
        }catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }


    @DeleteMapping("/{projId}")
    public ResponseEntity<DeleteResponse> deleteProject(@PathVariable Long projId){
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(projectService.deleteProject(projId));
        }catch (RuntimeException ex){
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

}
