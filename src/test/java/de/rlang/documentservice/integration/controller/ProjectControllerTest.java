package de.rlang.documentservice.integration.controller;

import de.rlang.documentservice.helper.AuthHelper;
import de.rlang.documentservice.helper.DTOFactory;
import de.rlang.documentservice.model.dto.in.CreateProjectDTO;
import de.rlang.documentservice.model.dto.in.UserDTO;
import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.model.entity.Project;
import de.rlang.documentservice.model.entity.User;
import de.rlang.documentservice.repository.ProjectRepository;
import de.rlang.documentservice.repository.UserRepository;
import de.rlang.documentservice.util.EntityToDTOConverter;
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


import java.util.Arrays;
import java.util.List;

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

    private User defaultUser;
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
        serverBaseUri = "http://localhost:" + port;
        defaultUser = dtoFactory.buildDefaultUser();
        userRepository.save(defaultUser);
    }

    @After
    public void tearDown() {
        projectRepository.deleteAll();
        userRepository.delete(defaultUser);
    }

    @Test
    public void createProject_Creates_Project_in_Database() {

        CreateProjectDTO createProjectDTO = new CreateProjectDTO("TestProject1");

        HttpEntity<CreateProjectDTO> request = new HttpEntity<>(createProjectDTO, authHelper.getAuthHeader());

        ResponseEntity<ProjectInformationDTO> responseEntity =
                template.exchange(
                        serverBaseUri + "/api/v1/projects",
                        HttpMethod.POST,
                        request,
                        ProjectInformationDTO.class);


        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        ProjectInformationDTO projectInformationDTO = responseEntity.getBody();

        assertThat(projectInformationDTO, notNullValue());
        assertThat(projectInformationDTO.getName(), is(createProjectDTO.getName()));
        assertThat(projectInformationDTO.getCreator(), is(dtoFactory.buildUserDTO(dtoFactory.buildDefaultUser())));

    }

    @Test
    public void createProject_Creates_Project_And_Adds_Participant() {
        UserDTO[] participants = {
                EntityToDTOConverter.ConvertToUserDTO(dtoFactory.buildUserWithRandomUUID(), UserDTO.class),
                EntityToDTOConverter.ConvertToUserDTO(dtoFactory.buildUserWithRandomUUID(), UserDTO.class)
        };

        CreateProjectDTO createProjectDTO = new CreateProjectDTO("TestProject1", Arrays.asList(participants));

        HttpEntity<CreateProjectDTO> request = new HttpEntity<>(createProjectDTO, authHelper.getAuthHeader());
        ProjectInformationDTO projectInformationDTO =
                template.postForObject(serverBaseUri + "/api/v1/projects", request, ProjectInformationDTO.class);

        assertThat(projectInformationDTO, notNullValue());
        assertThat(projectInformationDTO.getName(), is(createProjectDTO.getName()));
        assertThat(projectInformationDTO.getCreator(), is(dtoFactory.buildUserDTO(dtoFactory.buildDefaultUser())));
        assertThat(projectInformationDTO.getParticipants(), not(empty()));
    }

    @Test
    public void getProjectInformation_Returns_Correct_Information() {

        Project project = dtoFactory.buildProject("TestProject2", defaultUser);
        projectRepository.save(project);

        ResponseEntity<ProjectInformationDTO> response = template.exchange(
                serverBaseUri + "/api/v1/projects/" + project.getUuid().toString(),
                HttpMethod.GET,
                new HttpEntity<ProjectInformationDTO>(authHelper.getAuthHeader(defaultUser)),
                ProjectInformationDTO.class
        );

        ProjectInformationDTO projectInformation = response.getBody();

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(projectInformation.getName(), is(project.getName()));
        assertThat(projectInformation.getUuid(), is(project.getUuid()));
        assertThat(projectInformation.getCreator(),
                is(EntityToDTOConverter.ConvertToUserDTO(project.getCreator(), de.rlang.documentservice.model.dto.out.UserDTO.class)));
        assertThat(projectInformation.getCreatedAt(), is(project.getCreatedAt()));
    }

    @Test
    public void deleteProject_Removes_Project_From_Database() {
        Project project = dtoFactory.buildProject("TestProject3", defaultUser);
        projectRepository.save(project);


        ResponseEntity response = template.exchange(
                serverBaseUri + "/api/v1/projects/" + project.getUuid().toString(),
                HttpMethod.DELETE,
                new HttpEntity<Void>(authHelper.getAuthHeader(defaultUser)),
                Void.class);

        Project sameProject = projectRepository.findFirstByUuid(project.getUuid());

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
        assertThat(sameProject, is(nullValue()));
    }

    @Test
    public void deleteProject_Should_Reject_On_Unauthorized_User() {
        Project project = dtoFactory.buildProject("TestProject3", defaultUser);
        projectRepository.save(project);

        User randomUser = dtoFactory.buildUserWithRandomUUID();
        userRepository.save(randomUser);
        ResponseEntity response = template.exchange(
                serverBaseUri + "/api/v1/projects/" + project.getUuid().toString(),
                HttpMethod.DELETE,
                new HttpEntity<Void>(authHelper.getAuthHeader(randomUser)),
                Void.class);

        Project sameProject = projectRepository.findFirstByUuid(project.getUuid());

        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
        assertThat(sameProject, not(nullValue()));
    }
}
