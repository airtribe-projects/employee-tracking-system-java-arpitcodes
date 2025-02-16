package com.employee.management.system.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.employee.management.system.entity.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testFindByName() {
        // Arrange
        Project project = new Project();
        project.setpName("Test Project");
        projectRepository.save(project);

        // Act
        Project foundProject = projectRepository.findByName("Test Project");

        // Assert
        assertThat(foundProject).isNotNull();
        assertThat(foundProject.getpName()).isEqualTo("Test Project");
    }
}
