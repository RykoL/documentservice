package de.rlang.documentservice.integration.controller;

import de.rlang.documentservice.helper.AuthHelper;
import de.rlang.documentservice.helper.DTOFactory;
import de.rlang.documentservice.model.dto.in.CreateProjectDTO;
import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.model.entity.Project;
import de.rlang.documentservice.repository.ProjectRepository;
import de.rlang.documentservice.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ProjectControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    private AuthHelper authHelper;
    private DTOFactory dtoFactory;
    private String serverBaseUri;
    private TestRestTemplate template;

    public ProjectControllerTest() {
        dtoFactory = new DTOFactory();
        authHelper = new AuthHelper();
        template = new TestRestTemplate();
    }

    @Before
    public void setUp() {
        serverBaseUri = "http://localhost:" + String.valueOf(port);
        userRepository.save(dtoFactory.buildDefaultUser());
    }

    @After
    public void tearDown() {
        userRepository.delete(dtoFactory.buildDefaultUser());
    }

    @AfterEach
    public void clearProjects() {
        projectRepository.deleteAll();
    }

    @Test
    public void createProject_Creates_Project_in_Database() {

        CreateProjectDTO createProjectDTO = new CreateProjectDTO("TestProject1");

        HttpEntity<CreateProjectDTO> request = new HttpEntity<>(createProjectDTO, authHelper.getAuthHeader());
        ProjectInformationDTO projectInformationDTO =
                template.postForObject(serverBaseUri + "/api/v1/projects", request, ProjectInformationDTO.class);

        assertThat(projectInformationDTO, notNullValue());
        assertThat(projectInformationDTO.getName(), is(createProjectDTO.getName()));
        assertThat(projectInformationDTO.getCreator(), is(dtoFactory.buildUserDTO()));

    }

    @Test
    public void getProjectInformation_Returns_Correct_Information() {

        Project project = dtoFactory.buildProject("TestProject2", dtoFactory.buildDefaultUser());
        projectRepository.save(project);

        ResponseEntity<ProjectInformationDTO> response = template.exchange(
                serverBaseUri + "/api/v1/projects/" + project.getUuid().toString(),
                HttpMethod.GET,
                new HttpEntity<ProjectInformationDTO>(authHelper.getAuthHeader()),
                ProjectInformationDTO.class
        );

        ProjectInformationDTO projectInformation = response.getBody();

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(projectInformation.getName(), is(project.getName()));
        assertThat(projectInformation.getUuid(), is(project.getUuid()));
        assertThat(projectInformation.getCreator(), is(project.getCreator()));
        assertThat(projectInformation.getCreatedAt(), is(project.getCreatedAt()));
    }

    @Test
    public void deleteProject_Removes_Project_From_Database() {
        Project project = dtoFactory.buildProject("TestProject3", dtoFactory.buildDefaultUser());
        projectRepository.save(project);


        ResponseEntity response = template.exchange(
                serverBaseUri + "/api/v1/projects/" + project.getUuid().toString(),
                HttpMethod.DELETE,
                new HttpEntity<Void>(authHelper.getAuthHeader()),
                Void.class);

        Project sameProject = projectRepository.findFirstByUuid(project.getUuid());

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
        assertThat(sameProject, is(nullValue()));
    }
}
