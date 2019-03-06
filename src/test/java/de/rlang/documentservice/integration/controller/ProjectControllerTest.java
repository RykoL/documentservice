package de.rlang.documentservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rlang.documentservice.model.dto.in.CreateProjectDTO;
import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.repository.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ProjectControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void createProject_Creates_Project_in_Database() throws Exception{

        String serverBaseUri = "http://localhost:" + String.valueOf(port);

        ObjectMapper objectMapper = new ObjectMapper();

        CreateProjectDTO createProjectDTO = new CreateProjectDTO("TestProject1");

        RestTemplate template = new RestTemplate();
        HttpEntity<ProjectInformationDTO> request = new HttpEntity<>(new ProjectInformationDTO());
        template.postForObject(serverBaseUri + "/api/v1/projects", request, ProjectInformationDTO.class);
    }
}
