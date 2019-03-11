package de.rlang.documentservice.unit.service;

import de.rlang.documentservice.helper.DTOFactory;
import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.model.entity.Project;
import de.rlang.documentservice.repository.ProjectRepository;
import de.rlang.documentservice.service.ProjectService;
import de.rlang.documentservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectServiceTest {

    @TestConfiguration
    static class ProjectServiceTestContextConfiguration {

        @Bean
        public ProjectService projectServiceBean() {
            return new ProjectService();
        }

        @Bean
        public DTOFactory dtoFactoryBean() {
            return new DTOFactory();
        }
    }

    @MockBean
    ProjectRepository projectRepository;

    @MockBean
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    DTOFactory dtoFactory;

    @Before
    public void setUp() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(dtoFactory.buildDefaultUser());
    }

    @Test
    public void getProject_Returns_Project_By_Id() {

        Project testProject = dtoFactory.buildProject("TestProject", dtoFactory.buildDefaultUser());

        when(projectRepository.findFirstByUuid(testProject.getUuid())).thenReturn(testProject);

        ProjectInformationDTO projectInformation = projectService.getProject(testProject.getUuid());

        assertThat(projectInformation.getUuid(), is(testProject.getUuid()));
        assertThat(projectInformation.getName(), is(testProject.getName()));
        assertThat(projectInformation.getCreator(), is(testProject.getCreator()));
    }

    @Test
    public void getAllProject_Returns_All_Projects_For_User() {
        List<Project> projectList = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            projectList.add(dtoFactory.buildProject("TestProject" + String.valueOf(i), dtoFactory.buildDefaultUser()));
        }

        when(projectRepository.findAllByCreator(dtoFactory.buildDefaultUser())).thenReturn(projectList);

        List<ProjectInformationDTO> projectInformations = projectService.getAllProjects();

        assertThat(projectInformations, hasSize(3));
        assertThat(projectInformations, containsInAnyOrder(
                hasProperty("name", is("TestProject0")),
                hasProperty("name", is("TestProject1")),
                hasProperty("name", is("TestProject2"))
        ));
    }
}
