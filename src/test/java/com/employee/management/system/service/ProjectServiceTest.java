package com.employee.management.system.service;

import com.employee.management.system.entity.Project;
import com.employee.management.system.exception.ResourceNotFoundException;
import com.employee.management.system.model.DeleteResponse;
import com.employee.management.system.repository.EmployeeRepository;
import com.employee.management.system.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setpId(1L);
        project.setpName("Test Project");
        project.setpDesc("Test Description");
        project.setBudget(50000.0);
    }

    @Test
    void testCreateProject() {
        when(projectRepository.save(project)).thenReturn(project);
        Project createdProject = projectService.createProject(project);
        assertNotNull(createdProject);
        assertEquals("Test Project", createdProject.getpName());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testGetProjects() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project));
        List<Project> projects = projectService.getProjects();
        assertFalse(projects.isEmpty());
        assertEquals(1, projects.size());
    }

    @Test
    void testGetProjectByName_Found() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project));
        Project foundProject = projectService.getProjectByName("Test Project");
        assertNotNull(foundProject);
        assertEquals("Test Project", foundProject.getpName());
    }

    @Test
    void testGetProjectByName_NotFound() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project));
        Project foundProject = projectService.getProjectByName("Unknown Project");
        assertNull(foundProject);
    }

    @Test
    void testUpdateProject_Success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project updatedProject = projectService.updateProject(project);
        assertNotNull(updatedProject);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testUpdateProject_ResourceNotFoundException() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> projectService.updateProject(project));
        assertNotNull(exception);
    }

    @Test
    void testDeleteProject_Success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        doNothing().when(projectRepository).delete(project);

        DeleteResponse response = projectService.deleteProject(1L);
        assertNotNull(response);
        assertEquals("Project Deleted Successfully!", response.getMessage());
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
        verify(projectRepository, times(1)).delete(project);
    }

    @Test
    void testDeleteProject_ResourceNotFoundException() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> projectService.deleteProject(1L));
    }
}
