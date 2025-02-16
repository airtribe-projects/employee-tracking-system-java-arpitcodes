
package com.employee.management.system.controller;

import com.employee.management.system.entity.Project;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.DeleteResponse;
import com.employee.management.system.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock private ProjectService projectService;

    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        project = new Project();
        project.setpName("Test Project");
    }

    @Test
    void createProject_Conflict() {
        when(projectService.getProjectByName(any(String.class))).thenReturn(project);

        ResponseEntity<Project> response = projectController.createProject(project);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(projectService, never()).createProject(any(Project.class));
    }

    @Test void createProject_Created() {
        when(projectService.getProjectByName(any(String.class))).thenReturn(null);
        when(projectService.createProject(any(Project.class))).thenReturn(project);

        ResponseEntity<Project> response = projectController.createProject(project);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(project, response.getBody());
        verify(projectService, times(1)).createProject(any(Project.class));
    }

    @Test
    void createProject_ThrowsException() {
        when(projectService.getProjectByName(any(String.class))).thenThrow(new RuntimeException("Database Error"));

        assertThrows(ResourceNotFoundException.class, () -> projectController.createProject(project));
    }

    @Test void getAllProjects_ReturnsList() {
        List<Project> projects = Arrays.asList(project);
        when(projectService.getProjects()).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getAllProjects();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
        verify(projectService, times(1)).getProjects();
    }

    @Test void getAllProjects_ReturnsEmptyList() {
        when(projectService.getProjects()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Project>> response = projectController.getAllProjects();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
        verify(projectService, times(1)).getProjects();
    }

    @Test
    void getAllProjects_ThrowsException() {
        when(projectService.getProjects()).thenThrow(new RuntimeException("Database Error"));

        assertThrows(ResourceNotFoundException.class, () -> projectController.getAllProjects());
    }

    @Test void updateProject_Success() {
        when(projectService.updateProject(any(Project.class))).thenReturn(project);

        ResponseEntity<Project> response = projectController.updateProject(project);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project, response.getBody());
        verify(projectService, times(1)).updateProject(any(Project.class));
    }

    @Test
    void updateProject_ThrowsException() {
        when(projectService.updateProject(any(Project.class))).thenThrow(new RuntimeException("Update Error"));

        assertThrows(ResourceNotFoundException.class, () -> projectController.updateProject(project));
    }

    @Test void deleteProject_Success() {
        DeleteResponse deleteResponse = new DeleteResponse(204,"Project deleted successfully");
        when(projectService.deleteProject(any(Long.class))).thenReturn(deleteResponse);

        ResponseEntity<DeleteResponse> response = projectController.deleteProject(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(deleteResponse, response.getBody());
        verify(projectService, times(1)).deleteProject(any(Long.class));
    }

    @Test void deleteProject_ThrowsException() {
        when(projectService.deleteProject(any(Long.class))).thenThrow(new RuntimeException("Delete Error"));

        assertThrows(ResourceNotFoundException.class, () -> projectController.deleteProject(1L));
    }
}
